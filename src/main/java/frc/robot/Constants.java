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
}
