package frc.core.util.oi;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveteamRumble extends CommandBase{

  private static final int SECONDS_RUMBLE = 1;
  private static final int SET_OFF = 0;
  private static final int SET_ON = 1;

  private XboxController driver;
  private XboxController operator;

  private double isRumble;

  private Timer timer;

  public DriveteamRumble(XboxController driver, XboxController operator, boolean isRumble) {
    this.driver = driver;
    this.operator = operator;
    
    this.timer = new Timer();

    this.isRumble = isRumble ? SET_ON : SET_OFF;
  }

  @Override
  public void initialize() {
    this.timer.reset();
    this.timer.start();
  }

  @Override
  public void execute() {
    this.driver.setRumble(RumbleType.kLeftRumble, isRumble);
    this.operator.setRumble(RumbleType.kLeftRumble, isRumble);
  }

  @Override
  public boolean isFinished() {
    return this.timer.hasElapsed(SECONDS_RUMBLE);
  }

  @Override
  public void end(boolean isInterruptable) {
    this.operator.setRumble(RumbleType.kLeftRumble, SET_OFF);
    this.driver.setRumble(RumbleType.kLeftRumble, SET_OFF);
    this.timer.stop();
  }
}

