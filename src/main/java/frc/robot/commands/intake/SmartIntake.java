package frc.robot.commands.intake;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.util.NumberUtil;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Intake;

public class SmartIntake extends CommandBase{

  private static final int SECONDS_RELEASING_CARGO = 2;
  private static final double HIGH_BLUE_LEVEL = 600.0;
  private static final I2C.Port I2C_PORT = I2C.Port.kOnboard;
  private static final boolean IS_ADVERSARY_BLUE = true;

  private Intake intake;

  private ColorSensorV3 colorSensor;
  private DigitalInput infraredSensor;
  private Timer timer;

  private Color detectedColor;
  private boolean isReleaseCargo;

  public SmartIntake(Intake intake) {
    this.intake = intake;
    this.colorSensor = new ColorSensorV3(I2C_PORT);
    this.infraredSensor = new DigitalInput(IntakeConstants.infraredSensorPort); 
    this.isReleaseCargo = false;
  }

  @Override
  public void initialize() {
    this.timer.reset();
  }

  @Override
  public void execute() {
    this.getColor();

    if(this.isCollectedCargoBlue() && IS_ADVERSARY_BLUE) {
      this.isReleaseCargo = true;
    }

    if(this.isReleaseCargo) {
      this.timer.start();
      this.intake.set(NumberUtil.invert(IntakeConstants.speed));
    } else {
      this.intake.set(IntakeConstants.speed);
    }
  }



  private boolean isCollectedCargoBlue() {
    return isDetectedColorBlue();
  }

  private boolean isDetectedColorBlue() {
    return (this.detectedColor.blue / 2) > this.detectedColor.red;
  }

  private void getColor() {
    this.detectedColor = this.colorSensor.getColor();
  }

  @Override
  public boolean isFinished() {
    return this.timer.get() > SECONDS_RELEASING_CARGO;
  }

  @Override
  public void end(boolean isInterrupted) {
    this.intake.set(0);
    this.timer.reset();
  }
}
