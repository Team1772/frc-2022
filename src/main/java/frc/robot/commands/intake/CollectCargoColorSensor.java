package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.util.NumberUtil;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Intake;

public class CollectCargoColorSensor extends CommandBase {

  private boolean isAllianceRed;

  private Intake intake;

  private Color detectedColor;

  public CollectCargoColorSensor(Intake intake) {
    this.intake = intake;
    isAllianceRed = DriverStation.getAlliance().toString()
      .equalsIgnoreCase("Red");    
  }
  
  @Override
  public void execute() {
    this.getColor();

    System.out.println("------------------");
    System.out.println("is alliance red: " + isAllianceRed);
    System.out.println("is isdetected color red: " + this.isDetectedColorRed());
    System.out.println("is isdetected color blue: " + this.isDetectedColorBlue());
    System.out.println("is release cargo: " + this.isReleaseCargo());
    System.out.println("------------------");
    if (this.isReleaseCargo()) this.intake.set(NumberUtil.invert(IntakeConstants.speed));
    else this.intake.set(IntakeConstants.speed);
  }

  private boolean isReleaseCargo() {
    return (this.isDetectedColorBlue() && isAllianceRed) || (this.isDetectedColorRed() && !isAllianceRed);
  }

  private boolean isDetectedColorRed() {
    return (this.detectedColor.red / 1.5) > this.detectedColor.blue;
  }

  private boolean isDetectedColorBlue() {
    return (this.detectedColor.blue / 1.5) > this.detectedColor.red;
  }

  private void getColor() {
    this.detectedColor = this.intake.getColorDetected();
  }

  @Override
  public void end(boolean isInterrupted) {
    this.intake.stop();
  }
}