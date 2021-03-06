package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.util.NumberUtil;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;

public class ReleaseCargoSensor extends CommandBase {
  private final Intake intake;
  private final Buffer buffer;

  public ReleaseCargoSensor(Intake intake, Buffer buffer) {
    this.intake = intake;
    this.buffer = buffer;

    addRequirements(this.intake);
  }

  @Override
  public void execute() {
    if(this.buffer.isAnyInfraredOn()) {
      this.intake.set(NumberUtil.invert(0.35));
    }
  }

  @Override
  public void end(boolean isInterrupted) {
    this.intake.stop();
  }
}
