package frc.robot.commands.util;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class WaitSeconds extends CommandBase{
  private Timer timer;

  private double secondsToWait;
  
  public WaitSeconds(double secondsToWait) {
    this.timer = new Timer();
    this.secondsToWait = secondsToWait;
  }

  @Override
  public void initialize() {
    this.timer.reset();
    this.timer.start();
  }

  @Override
  public boolean isFinished() {
    return this.timer.get() > this.secondsToWait;
  }

  @Override
  public void end(boolean isInterrupted) {
    this.timer.stop();
  }
}
