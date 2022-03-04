package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.core.util.TrajectoryBuilder;
import frc.robot.commands.autonsEncoder.DriveReverseEncoders;
import frc.robot.commands.buffer.ForwardFeed;
import frc.robot.commands.buffer.ReleaseFeedTimer;
import frc.robot.commands.intake.CollectCargoTimer;
import frc.robot.commands.shooter.ShootAutonomous;
import frc.robot.commands.util.WaitSeconds;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutonomousTrajectoryBuilder extends SequentialCommandGroup {
  public AutonomousTrajectoryBuilder(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter,
      TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
        trajectoryBuilder.run("reverse")
    );
  }
}
