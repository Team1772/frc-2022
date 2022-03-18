package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public final class Constants {

  public static final class DrivetrainConstants {
    //motors
    public static final int
      motorLeftBack = 0,
      motorLeftFront = 1,
      motorRightBack = 3,
      motorRightFront = 2;

    public static final boolean 
      isMotorsLeftInverted = true,
      isMotorsRightInverted = false;

    //encoders
    public static final int
      encoderLeftPortOne = 6,
      encoderLeftPortTwo = 7,
      encoderRightPortOne = 8,
      encoderRightPortTwo = 9;

    public static final boolean
      isEncoderLeftInverted = true,
      isEncoderRightInverted = false;
    
    public static final int
      pulsesPerRotation = 500, 
			cyclesPerRevolution = pulsesPerRotation * 4;  

    //chassi
		public static final int 
      wheelRadius = 2;

    //voltageConstraint
		public static final double 
      ksVolts = 1.0072, //kS
      kvVoltSecondsPerMeter = 2.5362, //kV
      kaVoltSecondsSquaredPerMeter = 0.9221, //kA
      kTrackwidthMeters = 0.8067,
      differentialDriveVoltageConstraintMaxVoltage = 7;

    public static final DifferentialDriveKinematics
      kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

    public static final class PID {
      public static final double 
        kPDriveVelocity = 4.0807,
        kIDriveVelocity = 0,
        kDDriveVelocity = 0;
      
    }
  }

  public static final class LimelightConstants {
		public static final String tableName = "limelight";
		public static final int pipeline = 0;

    public static final class Seeking {
			public static final double kP = -0.1;
		}

		public static final class AimTarget {
			public static final double
        kP = 0.1, //standard Kp. We may tune manually.
				minCommand = 0.02; //we may test more or less minCommand. It cant be too high, since the robot would oscillate on aiming.
		}

    public static final class RangeTarget {
			public static final double 
				kPDistance = -0.13;
		}
		
		public static final class AimAndRangeTarget {
			public static final double 
        kP = -0.1,
				kPDistance = -0.50,
				minCommand = 0.04;
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

  public static final class ShooterConstants {
    public static final int motorPort = 1;

    public static final double speed = 0.53;
    public static final boolean isInverted = true;

    public static final double wheelRadius = 0.1016;

    public static final int
      maxRPM = 6232,
      kSensorUnitsPerRotation = 4096;

    public static final boolean 
      isMotorInverted = true,
      isSensorPhase = false;

    public static final class PID {
      public static final double 
        kPVelocity = 0.8,
        kIVelocity = 0,
        kDVelocity = 8,
        kFVelocity = 0.0341,
        kPeakOutputVelocity = 1,
        dutyCycle = 0.8;

      public static final int kIZoneVelocity = 0;   
    }
  }

  public static final class PIDTalonConstants {
    public static final boolean isSensorPhase = true;

    public static final int kPIDLoopIdx = 0,
      kTimeoutMs = 30,
      nominalOutputForwardValue = 0,
      nominalOutputReverseValue = 0,
      peakOutputForwardValue = 1,
      peakOutputReverseValue = -1;
  }

  public static final class IntakeConstants {
    public static final int	
      motorPort = 2,
      infraredSensorPort = 5;

    public static final double speed = 0.5;
    public static final boolean isInverted = true;

  }
  
  public static final class BufferConstants {
    public static final int 
      motorPort = 3,
      infraredBottomPort = 5,
      infraredTopPort = 4;

    public static final double speed = 0.45;
    public static final boolean isInverted = false;
  }
  
}
