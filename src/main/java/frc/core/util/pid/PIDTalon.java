package frc.core.util.pid;

import static frc.core.util.function.For.forWithCounter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants.PIDTalonConstants;

public abstract class PIDTalon {
	protected WPI_TalonSRX master;
	protected List<WPI_TalonSRX> followers;

	public PIDTalon(
		WPI_TalonSRX master, 
		boolean isMasterInverted, 
		boolean isFollowersInverted, 
		boolean isSensorPhase,
		double nominalOutputForwardValue, 
		double nominalOutputReverseValue, 
		double peakOutputForwardValue,	
		double peakOutputReverseValue, 
		Gains gains, 
		WPI_TalonSRX... followers
	) {
		this.master = master;
		this.followers = new ArrayList<>();

    if (followers.length > 0) {
		  this.followers = Arrays.stream(followers).collect(Collectors.toList());
      this.setFollowersInverted(isFollowersInverted);
      this.configMasterToFollowers();
    }
		
		this.configFactoryDefault();
		this.configSelectedFeedbackSensor();
		this.setSensorPhase(isSensorPhase);
		
		
		this.setMasterInverted(isMasterInverted);

		this.setOutputs(
			nominalOutputForwardValue, 
			nominalOutputReverseValue, 
			peakOutputForwardValue,
			peakOutputReverseValue
		);
		this.setPIDValues(gains);
	}

	private void configSelectedFeedbackSensor() {
		this.master.configSelectedFeedbackSensor(
			FeedbackDevice.CTRE_MagEncoder_Relative,
			PIDTalonConstants.kPIDLoopIdx,
			PIDTalonConstants.kTimeoutMs
		);
	}

	public void setMasterInverted(boolean isInverted) {
		this.master.setInverted(isInverted);
	}

	public void setFollowersInverted(Boolean... isInverted) {
		if (isInverted.length >= this.followers.size()) {
			forWithCounter(this.followers, (i, follower) -> {
				follower.setInverted(isInverted[i]);		
		 	});
		}
	}

	public void setFollowersInverted(Boolean isInverted) {
		this.followers.stream().forEach(follower -> follower.setInverted(isInverted));
	}

	public double getSelectedSensorVelocity() {
    return this.master.getSelectedSensorVelocity();
  }

  public double getClosedLoopError() {
    return this.master.getClosedLoopError();
  }

	private void configMasterToFollowers() {
		this.followers.stream()
								.forEach(follower -> follower.follow(this.master));
	}

	private void configFactoryDefault() {
		this.master.configFactoryDefault();

		if (this.followers.size() > 0) this.followers.stream()
								.forEach(follower -> follower.configFactoryDefault());
	}

	private void setSensorPhase(boolean isSensorPhase) {
		this.master.setSensorPhase(isSensorPhase);
	}

	private void setOutputs(
		double nominalOutputForwardValue, 
		double nominalOutputReverseValue,
		double peakOutputForwardValue, 
		double peakOutputReverseValue
	) {
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
