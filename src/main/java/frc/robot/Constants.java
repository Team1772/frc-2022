package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public final class Constants {
  public static final class DrivetrainConstants {
    //motors
    public static final int[] 
      motorRightPort = new int[] { 0, 1 },
      motorLeftPort = new int[] { 2, 3 };

    public static final boolean[] 
      isMotorsInverted = new boolean[] { true, false };

    //encoders
    public static final int[] 
      encoderRightPort = new int[] { 6, 7 },
      encoderLeftPort = new int[] { 8, 9 };

    public static final boolean[] 
      isEncodersInverted = new boolean[] { true, true };
    
    public static final int 
			pulsesRight = 500,
			pulsesLeft = 500;  

    //chassi
		public static final int 
      wheelRadius = 2;

    //voltageConstraint
		public static final double 
      ksVolts = 0.8408, //kS
      kvVoltSecondsPerMeter = 2.5464, //kV
      kaVoltSecondsSquaredPerMeter = 0.5173, //kA
      kTrackwidthMeters = 2.79,
      differentialDriveVoltageConstraintMaxVoltage = 7;

    public static final DifferentialDriveKinematics
      kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

    public static final class PID {
      public static final double 
        kPDriveVelocity = 3.8241,
        kIDriveVelocity = 0,
        kDDriveVelocity = 0;   
      
    }
  }

    public static final class OIConstants {
		  public static final int
		  	driverControllerPort = 0,
		  	operatorControllerPort = 1;
	  }

    public static final class AutoConstants {
      public static final double 
        kMaxSpeedMetersPerSecond = 2,
        kMaxAccelerationMetersPerSecondSquared = 2,
        kRamseteB = 2,
        kRamseteZeta = 0.7;
    }
  
}