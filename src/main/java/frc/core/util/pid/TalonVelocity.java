package frc.core.util.pid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants.PIDTalonConstants;
import frc.robot.Constants.ShooterConstants;

public class TalonVelocity extends PIDTalon {
  private double velocityUnitsPer100ms;

  public TalonVelocity(
    TalonSRX master, 
    boolean isMasterInverted,
    boolean isSensorPhase,
    double nominalOutputForwardValue, 
    double nominalOutputReverseValue, 
    double peakOutputForwardValue,
    double peakOutputReverseValue, 
    Gains gains
  ) {
    super(
      master, 
      isMasterInverted, 
      isSensorPhase,
      nominalOutputForwardValue,
      nominalOutputReverseValue, 
      peakOutputForwardValue, 
      peakOutputReverseValue, 
      gains
    );
  }

  public TalonVelocity(
    TalonSRX master,
    boolean isMasterInverted,
    boolean isSensorPhase,
    Gains gains
  ) {
    this(
      master,
      isMasterInverted,
      isSensorPhase,
      PIDTalonConstants.nominalOutputForwardValue,
      PIDTalonConstants.nominalOutputReverseValue,
      PIDTalonConstants.peakOutputForwardValue,
      PIDTalonConstants.peakOutputReverseValue,
      gains
    );
  }

  public TalonVelocity(
    TalonSRX master,
    Gains gains
  ) {
    this(
      master,
      false,
      PIDTalonConstants.isSensorPhase,
      PIDTalonConstants.nominalOutputForwardValue,
      PIDTalonConstants.nominalOutputReverseValue,
      PIDTalonConstants.peakOutputForwardValue,
      PIDTalonConstants.peakOutputReverseValue,
      gains
    );
  }

  private void setVelocity(double rpm, double dutyCycle) {
    dutyCycle = rpm > (ShooterConstants.maxRPM) ? dutyCycle : 1;
    this.velocityUnitsPer100ms = dutyCycle * rpm * 4096 / 600;

    super.master.set(ControlMode.Velocity, this.velocityUnitsPer100ms);
  }

  public void setRPM(double rpm, double dutyCycle) {
    this.setVelocity(rpm, dutyCycle);
  } 

  public void setVelocityMetersPerSecond(double velocityMetersPerSecond, double dutyCycle, double wheelRadius) {
    var rpm = (velocityMetersPerSecond * 60) / (2 * Math.PI * wheelRadius);

    this.setVelocity(rpm, dutyCycle);
  }

  public void stop() {
    super.master.set(ControlMode.PercentOutput, 0);
  }

  public boolean atSettedVelocity() {
    return super.master.getSelectedSensorVelocity() >= this.velocityUnitsPer100ms;
  }
}
