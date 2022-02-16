package frc.robot.commands.drivetrain.autonsTimer;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;


public class AutonomousTimer extends SequentialCommandGroup {
  private static final double time = 0;
  private static final double speed = 0;
  private static final double angle = 0;

  public AutonomousTimer(Drivetrain drivetrain) {
    super.addCommands(
      // new DriveForwardTimer(time, speed, drivetrain)
      // new RotateToAngle(angle, drivetrain),
      // new DriveReverseTimer(time, speed, drivetrain)
    );
  }
}