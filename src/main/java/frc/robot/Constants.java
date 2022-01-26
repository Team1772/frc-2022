package frc.robot;

public final class Constants {
  public static final class ShooterConstants {
    public static final int motorPort = 0;

    public static final int maxRPM = 6232;

    public static final boolean 
      isMotorInverted = true,
      isSensorPhase = false;

    public static final class PID {
      public static final double 
        kPVelocity = 0,
        kIVelocity = 0,
        kDVelocity = 0,
        kFVelocity = 0,
        kPeakOutputVelocity = 0,
        dutyCycle = 0;

      public static final int kIZoneVelocity = 0;
    }
  }

  public static final class PIDTalonConstants {
    public static final boolean isSensorPhase = true;

    public static final int kPIDLoopIdx = 0,
      kTimeoutMs = 0,
      nominalOutputForwardValue = 1,
      nominalOutputReverseValue = -1,
      peakOutputForwardValue = 1,
      peakOutputReverseValue = -1;
  }
}
