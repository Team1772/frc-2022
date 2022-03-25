package frc.core.util.pid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants.PIDTalonConstants;
import frc.robot.Constants.ShooterConstants;

public class TalonVelocity extends PIDTalon {

  private double velocityUnitsPer100ms;

  public TalonVelocity(
      WPI_TalonSRX master,
      boolean isMasterInverted,
      boolean isFollowersInverted,
      boolean isSensorPhase,
      double nominalOutputForwardValue,
      double nominalOutputReverseValue,
      double peakOutputForwardValue,
      double peakOutputReverseValue,
      Gains gains,
      WPI_TalonSRX... followers) {
    super(
        master,
        isMasterInverted,
        isFollowersInverted,
        isSensorPhase,
        nominalOutputForwardValue,
        nominalOutputReverseValue,
        peakOutputForwardValue,
        peakOutputReverseValue,
        gains,
        followers);
  }

  public TalonVelocity(
      WPI_TalonSRX master,
      boolean isMasterInverted,
      boolean isFollowersInverted,
      boolean isSensorPhase,
      Gains gains,
      WPI_TalonSRX... followers) {
    this(
        master,
        isMasterInverted,
        isFollowersInverted,
        isSensorPhase,
        PIDTalonConstants.nominalOutputForwardValue,
        PIDTalonConstants.nominalOutputReverseValue,
        PIDTalonConstants.peakOutputForwardValue,
        PIDTalonConstants.peakOutputReverseValue,
        gains,
        followers);
  }

  public TalonVelocity(
    WPI_TalonSRX master,
    boolean isMasterInverted,
    boolean isSensorPhase,
    Gains gains,
    WPI_TalonSRX... followers) {
  this(
      master,
      isMasterInverted,
      false,
      isSensorPhase,
      PIDTalonConstants.nominalOutputForwardValue,
      PIDTalonConstants.nominalOutputReverseValue,
      PIDTalonConstants.peakOutputForwardValue,
      PIDTalonConstants.peakOutputReverseValue,
      gains,
      followers);
}

  public TalonVelocity(
      WPI_TalonSRX master,
      Gains gains,
      WPI_TalonSRX... followers) {
    this(
        master,
        false,
        false,
        PIDTalonConstants.isSensorPhase,
        PIDTalonConstants.nominalOutputForwardValue,
        PIDTalonConstants.nominalOutputReverseValue,
        PIDTalonConstants.peakOutputForwardValue,
        PIDTalonConstants.peakOutputReverseValue,
        gains,
        followers);
  }

  private void setVelocity(double rpm, double dutyCycle) {
    dutyCycle = rpm > (ShooterConstants.maxRPM) ? dutyCycle : 1;
    this.velocityUnitsPer100ms = dutyCycle * rpm * 4096 / 600;

    super.master.set(ControlMode.Velocity, velocityUnitsPer100ms);
  }

  public void setRPM(double rpm, double dutyCycle) {
    this.setVelocity(rpm, dutyCycle);
  }

  public void setVelocityMetersPerSecond(double velocityMetersPerSecond, double dutyCycle, double wheelRadius) {
    var rpm = (velocityMetersPerSecond * 60) / (2 * Math.PI * wheelRadius);

    this.setVelocity(rpm, dutyCycle);
  }

  public void stop() {
    super.master.set(ControlMode.Velocity, 0);

  }

  public boolean atSettedVelocity() {
    if(this.getSelectedSensorVelocity() == 0) return false;

    return (this.velocityUnitsPer100ms - super.master.getSelectedSensorVelocity()) <= 50 ;
  }

  public double getSelectedSensorVelocity() {
    return super.master.getSelectedSensorVelocity();
  }
}