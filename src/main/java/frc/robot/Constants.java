package frc.robot;

public final class Constants {
  public static final class DrivetrainConstants {
    //motors
    public static final int[] 
      motorLeftPort = new int[] { 0, 1 },
      motorRightPort = new int[] { 2, 3 };

    public static final boolean[] 
      isMotorsInverted = new boolean[] { true, false };

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
}
