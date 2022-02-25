package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BufferConstants;

public class Buffer extends SubsystemBase {
  private final VictorSPX motor;
  private DigitalInput infraredSensorBottom;
  private DigitalInput infraredSensorTop;


  public Buffer() {
    this.motor = new VictorSPX(BufferConstants.motorPort);

    this.motor.setInverted(BufferConstants.isInverted);
    this.infraredSensorBottom = new DigitalInput(BufferConstants.infraredSensorBottomPort);
    this.infraredSensorTop = new DigitalInput(BufferConstants.infraredSensorTopPort);
  }

  public void set(double speed) {
    this.motor.set(ControlMode.PercentOutput, speed);
  }

  public boolean isInfraredBottomOn() {
    return !this.infraredSensorBottom.get();
  }

  public boolean isInfraredBottomOff() {
    return this.infraredSensorTop.get();
  }

  public boolean isInfraredTopOn() {
    return !this.infraredSensorTop.get();
  }

  public boolean isInfraredTopOff() {
    return this.infraredSensorTop.get();
  }

  public boolean isAnyInfraredOn() {
    return this.isInfraredBottomOn() || this.isInfraredTopOn();
  }

  public boolean isAllInfraredsOff() {
    return this.isInfraredBottomOff() || this.isInfraredTopOff();
  }

  public void stop() {
    this.motor.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("infrared bottom", !this.infraredSensorBottom.get());
    SmartDashboard.putBoolean("infrared top", !this.infraredSensorBottom.get());
  }
}
