package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.util.pid.Gains;
import frc.core.util.pid.TalonVelocity;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
	private final WPI_TalonSRX motor;
	private final TalonVelocity shooterPID;

	public Shooter() {
		this.motor = new WPI_TalonSRX(Constants.ShooterConstants.motorPort);

		this.shooterPID = new TalonVelocity(
				this.motor,
				ShooterConstants.isMotorInverted,
				ShooterConstants.isSensorPhase,
				new Gains(
						ShooterConstants.PID.kPVelocity,
						ShooterConstants.PID.kIVelocity,
						ShooterConstants.PID.kDVelocity,
						ShooterConstants.PID.kFVelocity,
						ShooterConstants.PID.kIZoneVelocity,
						ShooterConstants.PID.kPeakOutputVelocity));
	}

	public void set(double speed) {
		this.motor.set(ControlMode.PercentOutput, speed);
	}

	public void setRPM(double rpm) {
		this.shooterPID.setRPM(
				rpm,
				ShooterConstants.PID.dutyCycle);
	}

	public void setVelocityMetersPerSecond(double velocityMetersPerSecond) {
		this.shooterPID.setVelocityMetersPerSecond(
				velocityMetersPerSecond,
				ShooterConstants.PID.dutyCycle,
				ShooterConstants.wheelRadius);
	}

	public boolean atSettedVelocity() {
		return this.shooterPID.atSettedVelocity();
	}

	public boolean isSensorVelocityPositive() {
		return this.shooterPID.getSelectedSensorVelocity() >= 0;
	}

	public void stop() {
		this.set(0);
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("error", shooterPID.getClosedLoopError());
		SmartDashboard.putNumber("selected sensor velocity", this.shooterPID.getSelectedSensorVelocity());
		SmartDashboard.putBoolean("isSettedVelocity", this.atSettedVelocity());
	}
}
