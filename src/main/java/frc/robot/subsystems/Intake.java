package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ColorSensorV3;

public class Intake extends SubsystemBase {

  private static final int MINIMUM_PROXIMITY_WITHOUT_CARGO = 200;

  private static final I2C.Port I2C_PORT = I2C.Port.kOnboard;

  private VictorSPX motor;
  private final ColorSensorV3 colorSensor;

  private boolean isReleaseCargo;

  public Intake() {
    this.motor = new VictorSPX(IntakeConstants.motorPort);
    this.motor.setInverted(IntakeConstants.isInverted);
    this.colorSensor = new ColorSensorV3(I2C_PORT);

    this.isReleaseCargo = false;
  }

  public void set(double speed) {
    this.motor.set(ControlMode.PercentOutput, speed);
  }

  public Color getColorDetected() {
     return this.colorSensor.getColor();
  }

  public boolean isCargoOnIntake() {
    return this.isProximityDetectingCargo();
  }

  public boolean isProximityDetectingCargo() {
    return this.colorSensor.getProximity() > MINIMUM_PROXIMITY_WITHOUT_CARGO;
  }

  public boolean isReleaseCargo() {
    return this.isReleaseCargo;
  }

  public void setReleaseCargo(boolean isReleaseCargo) {
    this.isReleaseCargo = isReleaseCargo;
  }

  public void stop() {
    this.motor.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Is cargo on intake", this.isCargoOnIntake());
    SmartDashboard.putNumber("IR", this.colorSensor.getProximity());
    SmartDashboard.putNumber("R", this.colorSensor.getRed());
    SmartDashboard.putNumber("G", this.colorSensor.getBlue());
    SmartDashboard.putNumber("B", this.colorSensor.getGreen());
  }
}
