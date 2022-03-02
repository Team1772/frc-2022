package frc.robot.commands.autonsEncoder;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class WaitSeconds extends CommandBase{
  private Timer timer;

  private double secondsToWait;
  
  public WaitSeconds(double secondsToWait) {
    this.timer = new Timer();
    this.secondsToWait = secondsToWait;
  }
}
