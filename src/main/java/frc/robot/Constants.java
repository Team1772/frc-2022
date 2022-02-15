package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public final class Constants {
  public static final class DrivetrainConstants {
    //motors
    public static final int
      motorLeftBack = 0,
      motorLeftFront = 1,
      motorRightBack = 2,
      motorRightFront = 3;

    public static final boolean 
      isMotorsLeftInverted = true,
      isMotorsRightInverted = false;

    //encoders
    public static final int
      encoderRightPortOne = 6,
      encoderRightPortTwo = 7,
      encoderLeftPortOne = 8,
      encoderLeftPortTwo = 9;

    public static final boolean
      isEncoderLeftInverted = true,
      isEncoderRightInverted = true;
    
    public static final int
      pulsesPerRotation = 500, 
			cyclesPerRevolution = pulsesPerRotation * 4;  

    //chassi
		public static final int 
      wheelRadius = 2;

    //voltageConstraint
		public static final double 
      ksVolts = 1.103, //kS
      kvVoltSecondsPerMeter = 2.6508, //kV
      kaVoltSecondsSquaredPerMeter = 0.4465, //kA
      kTrackwidthMeters = 0.8523,
      differentialDriveVoltageConstraintMaxVoltage = 7;

    public static final DifferentialDriveKinematics
      kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

    public static final class PID {
      public static final double 
        kPDriveVelocity = 3.8848,
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

  public static final class IntakeConstants {
    public static final int	motorPort = 2;
  }
  
}
