package frc.robot.commands.autonsEncoder;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;
import frc.robot.commands.buffer.ReleaseFeedTimer;
import frc.robot.commands.intake.CollectCargoTimer;
import frc.robot.commands.shooter.ShootAutonomous;
import frc.robot.commands.util.WaitSeconds;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutonomousEncoders extends SequentialCommandGroup {
  public AutonomousEncoders(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter, TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
       new ParallelCommandGroup(
         new DriveReverseEncoders(0.3, 0.8, drivetrain),
         new ReleaseFeedTimer(0.5, buffer)
       ),
      new ShootAutonomous(2, intake, buffer, shooter),
      new DriveReverseEncoders(0.5, 0.8, drivetrain),
      new RotateToAngle(-64, drivetrain),
      new ParallelCommandGroup(
        new DriveFowardEncoders(0.3, 0.8, drivetrain),
        new CollectCargoTimer(2, intake)
      ),
      new RotateToAngle(76, drivetrain),
      new DriveFowardEncoders(0.1, 0.8, drivetrain),
      new WaitSeconds(1),
      new ShootAutonomous(2, intake, buffer, shooter)
    );
  }
}