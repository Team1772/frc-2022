package frc.core.util.pid;

public class Gains {
  protected final double kP, kI, kD, kF, kPeakOutput;
	protected final int kIzone;

	public Gains(double kP, double kI, double kD, double kF, int kIzone, double kPeakOutput) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
		this.kIzone = kIzone;
		this.kPeakOutput = kPeakOutput;
	}
}
