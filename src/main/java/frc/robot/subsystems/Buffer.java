package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BufferConstants;

public class Buffer extends SubsystemBase {
  private final VictorSPX motor;
  private final DigitalInput infraredBottom;
  private final DigitalInput infraredTop;

  public Buffer() {
    this.motor = new VictorSPX(BufferConstants.motorPort);
    this.infraredBottom = new DigitalInput(BufferConstants.infraredBottomPort);
    this.infraredTop = new DigitalInput(BufferConstants.infraredTopPort);

    this.motor.setInverted(BufferConstants.isInverted);
  }

  public void set(double speed) {
    this.motor.set(ControlMode.PercentOutput, speed);
  }

  public boolean isInfraredBottomOn() {
    return !this.infraredBottom.get();
  }

  public boolean isInfraredTopOn() {
    return !this.infraredTop.get();
  }

  public boolean isInfraredBottomOff() {
    return this.infraredBottom.get();
  }

  public boolean isInfraredTopOff() {
    return this.infraredTop.get();
  }

  public boolean isAllInfraredsOff() {
    return this.isInfraredBottomOff() && this.isInfraredTopOff();
  }

  public void stop() {
    this.motor.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("IR Bottom", this.isInfraredBottomOn());
    SmartDashboard.putBoolean("IR Top", this.isInfraredTopOn());
  }
}
