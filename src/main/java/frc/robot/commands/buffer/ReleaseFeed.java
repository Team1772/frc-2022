package frc.robot.commands.buffer;

import frc.core.util.NumberUtil;
import frc.robot.subsystems.Buffer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ReleaseFeed extends CommandBase {
  private final Buffer buffer;
  private double speed;

  public ReleaseFeed(double speed, Buffer buffer) {
    this.buffer = buffer;
    this.speed = speed;

    addRequirements(this.buffer);
  }

  @Override
  public void execute() {
    this.buffer.set(NumberUtil.invert(speed));
  }

  @Override
  public void end(boolean interrupted) {
    this.buffer.stop();
  }
}
