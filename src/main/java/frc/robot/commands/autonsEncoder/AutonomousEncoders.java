package frc.robot.commands.autonsEncoder;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;


public class AutonomousEncoders extends SequentialCommandGroup {
  private static final double oneMeter = 1.0;
  private static final double twoMeters = 2.0;
  private static final double threeMeters = 3.0;
  private static final double fourMeters = 4.0;
  private static final double fiveMeters = 5.0;
  private static final double speed = 0.52;
  private static final double angle = 90;

  public AutonomousEncoders(Drivetrain drivetrain) {
    super.addCommands(
      new DriveFowardEncoders(3.4, speed, drivetrain),
      new RotateToAngle(90, drivetrain),
      new DriveReverseEncoders(2.5, speed, drivetrain)
    );
  }
}
