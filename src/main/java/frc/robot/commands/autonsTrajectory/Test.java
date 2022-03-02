package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;
import frc.robot.commands.autonsEncoder.DriveFowardEncoders;
import frc.robot.commands.autonsEncoder.DriveReverseEncoders;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Test extends SequentialCommandGroup {

  public Test(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter, TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      new DriveReverseEncoders(0.3, 1, drivetrain),
      new AutonomousShoot(2, intake, buffer, shooter)
    );
  }
}