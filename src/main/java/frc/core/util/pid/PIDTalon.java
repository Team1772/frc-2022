package frc.core.util.pid;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants.PIDTalonConstants;

public class PIDTalon {
  protected TalonSRX master;

  public PIDTalon(
    TalonSRX master,
    boolean isMasterInverted,
    boolean isSensorPhase,
    double nominalOutputForwardValue,
    double nominalOutputReverseValue,
    double peakOutputForwardValue,
    double peakOutputReverseValue,
    Gains gains) {
  this.master = master;

  this.configFactoryDefault();
  this.configSelectedFeedbackSensor();
  this.setSensorPhase(isSensorPhase);
  this.setMasterInverted(isMasterInverted);

  this.setOutputs(
    nominalOutputForwardValue,
    nominalOutputReverseValue,
    peakOutputForwardValue,
    peakOutputReverseValue);
  this.setPIDValues(gains);
  }

  private void configSelectedFeedbackSensor() {
    this.master.configSelectedFeedbackSensor(
      FeedbackDevice.CTRE_MagEncoder_Relative,
      PIDTalonConstants.kPIDLoopIdx,
      PIDTalonConstants.kTimeoutMs);
  }

  public void setMasterInverted(boolean isInverted) {
    this.master.setInverted(isInverted);
  }

  public double getSelectedSensorVelocity() {
    return this.master.getSelectedSensorVelocity();
  }

  public double getClosedLoopError() {
    return this.master.getClosedLoopError();
  }

  private void configFactoryDefault() {
    this.master.configFactoryDefault();
  }

  private void setSensorPhase(boolean isSensorPhase) {
    this.master.setSensorPhase(isSensorPhase);
  }

  private void setOutputs(
      double nominalOutputForwardValue,
      double nominalOutputReverseValue,
      double peakOutputForwardValue,
      double peakOutputReverseValue) {
    this.master.configNominalOutputForward(nominalOutputForwardValue, PIDTalonConstants.kTimeoutMs);
    this.master.configNominalOutputReverse(nominalOutputReverseValue, PIDTalonConstants.kTimeoutMs);
    this.master.configPeakOutputForward(peakOutputForwardValue, PIDTalonConstants.kTimeoutMs);
    this.master.configPeakOutputReverse(peakOutputReverseValue, PIDTalonConstants.kTimeoutMs);
  }

  private void setPIDValues(Gains gains) {
    this.master.config_kF(PIDTalonConstants.kPIDLoopIdx, gains.kF, PIDTalonConstants.kTimeoutMs);
    this.master.config_kP(PIDTalonConstants.kPIDLoopIdx, gains.kP, PIDTalonConstants.kTimeoutMs);
    this.master.config_kI(PIDTalonConstants.kPIDLoopIdx, gains.kI, PIDTalonConstants.kTimeoutMs);
    this.master.config_kD(PIDTalonConstants.kPIDLoopIdx, gains.kD, PIDTalonConstants.kTimeoutMs);
  }
}
