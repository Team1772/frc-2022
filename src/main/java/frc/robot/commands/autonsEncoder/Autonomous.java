package frc.robot.commands.autonsEncoder;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;


public class Autonomous extends SequentialCommandGroup {
  private static final double twoMeters = 2.0;
  private static final double speed = 0;
  private static final double angle = 0;

  public Autonomous(Drivetrain drivetrain) {
    super.addCommands(
      new DriveFowardEncoders(twoMeters, speed, drivetrain),
      new TurnToAngle(angle, drivetrain),
      new DriveReverseEncoders(twoMeters, speed, drivetrain)
    );
  }
}
