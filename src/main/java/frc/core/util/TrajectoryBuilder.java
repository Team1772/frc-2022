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
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.trajectory.Trajectory.State;
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
  private Map<Integer, String> currentTrajectories;
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
        });
  }

  public Command build(boolean updateOdometry, String previousFileName, String filesName) {
    Trajectory actualTrajectory = this.trajectories
        .entrySet()
        .stream()
        .filter(trajectory -> filesName
            .contains(trajectory.getKey()))
        .map(Entry::getValue)
        .collect(Collectors.toList())
        .get(0);
    Trajectory previousTrajectory = this.trajectories
        .entrySet()
        .stream()
        .filter(trajectory -> previousFileName
            .contains(trajectory.getKey()))
        .map(Entry::getValue)
        .collect(Collectors.toList())
        .get(0);

    actualTrajectory = continueTrajectory(previousTrajectory, actualTrajectory);

    this.createRamsete(actualTrajectory, updateOdometry);

    if (updateOdometry)
      this.drivetrain.resetOdometry(actualTrajectory.getInitialPose());

    return this.getRamsete().andThen(
        () -> {
          this.drivetrain.tankDriveVolts(0, 0);
        });
  }

  public Command run(String... filesNames) {
    return this.build(true, filesNames);
  }

  public Command run(String fileName) {
    if (isNull(currentTrajectories)) return this.build(true, fileName);

    int trajectoryPosition = this.currentTrajectories
        .entrySet()
        .stream()
        .filter(trajectoryMap -> fileName
            .equals(trajectoryMap.getValue().toString()))
        .map(Entry::getKey)
        .collect(Collectors.toList())
        .get(0)
        .intValue();
    String trajectoryName = this.currentTrajectories
        .entrySet()
        .stream()
        .filter(trajectoryMap -> fileName
            .equals(trajectoryMap.getValue().toString()))
        .map(Entry::getValue)
        .collect(Collectors.toList())
        .get(0);

    if (trajectoryPosition > 1) {
      var previousTrajectory = this.currentTrajectories
          .entrySet()
          .stream()
          .filter(trajectoryMap -> (trajectoryPosition - 1) == trajectoryMap.getKey().intValue())
          .map(Entry::getValue)
          .collect(Collectors.toList())
          .get(0);

      return this.build(false, previousTrajectory, trajectoryName);
    }

    return this.build(true, trajectoryName);
  }

  public void addCurrentTrajectories(Map<Integer, String> currentTrajectories) {
    this.currentTrajectories = currentTrajectories;
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

  /**
   * Re-locate and rotate a trajectory
   * 
   * @param previous   Previous trajectory
   * @param trajectory Trajectory to translates
   * @return Transformed trajectory where initial state is at end of previous
   *         trajectory
   */
  private Trajectory continueTrajectory(final Trajectory previous, final Trajectory trajectory) {
    return makeTrajectoryStartAt(trajectory, getEndPose(previous));
  }

  /**
   * @param trajectory Trajectory
   * @return End state
   */
  private State getEnd(final Trajectory trajectory) {
    final List<State> states = trajectory.getStates();
    return states.get(states.size() - 1);
  }

  /**
   * @param trajectory Trajectory
   * @return End pose
   */
  private Pose2d getEndPose(final Trajectory trajectory) {
    return getEnd(trajectory).poseMeters;
  }

  /**
   * Re-locate and rotate a trajectory
   * 
   * @param Original trajectory
   * @param start    Startpoint and heading
   * @return Transformed trajectory where initial state is 'start'
   */
  private Trajectory makeTrajectoryStartAt(final Trajectory trajectory, final Pose2d start) {
    // The relativeTo() methods in Pose2d and Trajectory are basically
    // a "substract" operation for X, Y and the rotation angle.
    // Assume a trajectory that moves from "5" to "6", and start is at "2".
    // The offset becomes "5" - "2" = "3"
    final Transform2d offset = start.minus(trajectory.getInitialPose());
    // Computing the "5 -> 6" trajectory relative to "3" becomes "2 -> 3".
    // So we get a trajectory that moves by "1" unit, starting at "2".
    return trajectory.transformBy(offset);
  }
}
