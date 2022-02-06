package frc.robot;

public final class Constants {
  public static final class DrivetrainConstants {
    //motors
    public static final int[] 
      motorRightPort = new int[] { 0, 1 },
      motorLeftPort = new int[] { 2, 3 };

    public static final boolean[] 
      isMotorsInverted = new boolean[] { true, true };

    //encoders
    public static final int[] 
      encoderRightPort = new int[] { 6, 7 },
      encoderLeftPort = new int[] { 8, 9 };

    public static final boolean[] 
      isEcondersInverted = new boolean[] { true, true };
    
    public static final int 
			pulsesRight = 0,
			pulsesLeft = 0;  

    //chassi
		public static final int 
      wheelRadius = 0;
      
  }

  public static final class LimelightConstants {
		public static final String tableName = "limelight";
		public static final int pipeline = 0;

		public static final class AimTarget {
			public static final 
        double kP = 0,
				minCommand = 0;
		}
		
		public static final class AimAndRangeTarget {
			public static final 
        double kP = 0,
				kPDistance = 0,
				minCommand = 0;
		}

		public static final class Seeking {
			public static final double kP = 0;
		}
	}
}
