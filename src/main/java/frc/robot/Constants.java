// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    
    public static final class DrivetrainConstants {


        //motores
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
}



}
