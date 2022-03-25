package frc.robot.commands.buffer;

import frc.core.util.NumberUtil;
import frc.robot.Constants.BufferConstants;
import frc.robot.subsystems.Buffer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ReleaseFeedSensor extends CommandBase {
  private final Buffer buffer;

  public ReleaseFeedSensor(Buffer buffer) {
    this.buffer = buffer;

    addRequirements(this.buffer);
  }

  @Override
  public void execute() {
    if(this.buffer.isAnyInfraredOn()) {
      this.buffer.set(NumberUtil.invert(0.35));
    }
  }

  @Override
  public void end(boolean interrupted) {
    this.buffer.stop();
  }
}
