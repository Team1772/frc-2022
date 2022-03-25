package frc.core.util.oi;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveteamRumble extends CommandBase{

  private static final int SET_OFF = 0;
  private static final int SET_ON = 1;

  private XboxController driver;
  private XboxController operator;

  private double isRumble;

  public DriveteamRumble(XboxController driver, XboxController operator, boolean isRumble) {
    this.driver = driver;
    this.operator = operator;
    
    this.isRumble = isRumble ? SET_ON : SET_OFF;
  }

  @Override
  public void execute() {
    this.driver.setRumble(RumbleType.kLeftRumble, isRumble);
    this.operator.setRumble(RumbleType.kLeftRumble, isRumble);
  }

  @Override
  public void end(boolean isInterruptable) {
    this.operator.setRumble(RumbleType.kLeftRumble, SET_OFF);
    this.driver.setRumble(RumbleType.kLeftRumble, SET_OFF);
  }
}

