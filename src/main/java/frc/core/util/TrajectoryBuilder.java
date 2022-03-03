package frc.core.util;

import static java.util.Objects.isNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.subsystems.Drivetrain;

public class TrajectoryBuilder {

  private static final int FIRST_TRAJECTORY_INDEX = 0;

  private Drivetrain drivetrain;

  private final SimpleMotorFeedforward simpleMotorFeedforward;
  private final PIDController pidController;
  private final RamseteController ramseteController;

  private Map<String, Trajectory> trajectories;
  private RamseteCommand ramseteCommand;

  public TrajectoryBuilder(Drivetrain drivetrain, String... filesNames) {
    this.drivetrain = drivetrain;

    this.trajectories = this.mapTrajectories(filesNames);

    this.simpleMotorFeedforward = new SimpleMotorFeedforward(
        DrivetrainConstants.ksVolts,
        DrivetrainConstants.kvVoltSecondsPerMeter,
        DrivetrainConstants.kaVoltSecondsSquaredPerMeter);
    this.pidController = new PIDController(
        DrivetrainConstants.PID.kPDriveVelocity,
        DrivetrainConstants.PID.kIDriveVelocity,
        DrivetrainConstants.PID.kDDriveVelocity);
    this.ramseteController = new RamseteController(
        AutoConstants.kRamseteB,
        AutoConstants.kRamseteZeta);
  }

  public Map<String, Trajectory> mapTrajectories(String... filesNames) {
    return Arrays.stream(filesNames)
        .collect(Collectors.toMap(
            fileName -> fileName,
            this::createTrajectory));
  }

  public void createRamsete(Trajectory trajectory, boolean updateOdometry) {
    if (isNull(trajectory)) {
      DriverStation.reportError(
          "trajectory is null",
          new Exception().getStackTrace());
    } else {
      this.ramseteCommand = new RamseteCommand(
          trajectory,
          this.drivetrain::getPose,
          this.ramseteController,
          this.simpleMotorFeedforward,
          DrivetrainConstants.kDriveKinematics,
          this.drivetrain::getWheelSpeeds,
          this.pidController,
          this.pidController,
          this.drivetrain::tankDriveVolts,
          this.drivetrain);
    }
  }

  public Command build(boolean updateOdometry, String... filesNames) {
    var trajectories = this.trajectories
        .entrySet()
        .stream()
        .filter(trajectory -> Set.of(filesNames)
            .contains(trajectory.getKey()))
        .map(Entry::getValue)
        .collect(Collectors.toList());

    var trajectory = trajectories.size() > 1 ? this.concatenate(trajectories)
        : trajectories.get(FIRST_TRAJECTORY_INDEX);

    this.createRamsete(trajectory, updateOdometry);

    this.drivetrain.resetOdometry(trajectory.getInitialPose());

    return this.getRamsete().andThen(
        () -> {
          this.drivetrain.tankDriveVolts(0, 0);
          this.drivetrain.resetOdometry(trajectory.getInitialPose());
        }
    );
  }

  public Command run(String... filesNames) {
    return this.build(true, filesNames);
  }

  private RamseteCommand getRamsete() {
    return this.ramseteCommand;
  }

  private Trajectory createTrajectory(String fileName) {
    String path = String.format("paths/output/%s.wpilib.json", fileName);
    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(path);

      return TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    } catch (IOException ex) {
      DriverStation.reportError(
          String.format("Unable to open trajectory: %s", path),
          ex.getStackTrace());

      return null;
    }
  }

  private Trajectory concatenate(List<Trajectory> trajectories) {
    var finalTrajectory = trajectories.get(FIRST_TRAJECTORY_INDEX);

    for (var trajectory : trajectories.subList(1, trajectories.size())) {
      finalTrajectory = finalTrajectory.concatenate(trajectory);
    }

    return finalTrajectory;
  }
}
