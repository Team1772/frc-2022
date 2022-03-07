package frc.robot.commands.buffer;

import frc.robot.Constants.BufferConstants;
import frc.robot.subsystems.Buffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ForwardFeedTimer extends CommandBase {
  private final Buffer buffer;

  private Timer timer;

  private double secondsEnabled;

  public ForwardFeedTimer(double secondsEnabled, Buffer buffer) {
    this.buffer = buffer;
    this.timer = new Timer();
    this.secondsEnabled = secondsEnabled;

    addRequirements(this.buffer);
  }

  @Override
  public void initialize() {
    this.timer.reset();
    this.timer.start();
  }

  @Override
  public void execute() {
    this.buffer.set(BufferConstants.speed);
  }

  @Override
  public boolean isFinished() {
    return this.timer.get() > this.secondsEnabled;
  }

  @Override
  public void end(boolean interrupted) {
    this.buffer.stop();
  }
}
