package frc.robot.commands.intake;

import frc.robot.Constants.BufferConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SmartFeed extends CommandBase {
  private final Intake intake;
  private final Buffer buffer;

  public SmartFeed(Intake intake, Buffer buffer) {
    this.buffer = buffer;
    this.intake = intake;

    addRequirements(this.buffer, this.intake);
  }

  @Override
  public void execute() {
    this.intake.set(IntakeConstants.speed);
    this.buffer.set(BufferConstants.speed);
  }
  @Override
  public boolean isFinished() {
    return this.buffer.isInfraredBottomOn();
  }

  @Override
  public void end(boolean interrupted) {
    this.intake.stop();
    this.buffer.stop();
  }

}
