package frc.robot.commands.autonsEncoder;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;


public class AutonomousEncoders extends SequentialCommandGroup {
  private static final double oneMeter = 1.0;
  private static final double speed = 0.3;
  private static final double angle = 0;

  public AutonomousEncoders(Drivetrain drivetrain) {
    super.addCommands(
      new Test(oneMeter, speed, drivetrain)
      // new DriveFowardEncoders(oneMeter, speed, drivetrain)
      // new RotateToAngle(angle, drivetrain),
      // new DriveReverseEncoders(oneMeter, speed, drivetrain)
    );
  }
}
