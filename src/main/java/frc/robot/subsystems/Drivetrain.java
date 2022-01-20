package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;


public class Drivetrain extends SubsystemBase {

    private final MotorControllerGroup motorsRight, motorsLeft;
    private final DifferentialDrive drive;
    private final Encoder encoderRight, encoderLeft;


    public Drivetrain() {

    
    this.motorsRight = new MotorControllerGroup(
      new VictorSP(DrivetrainConstants.motorRightPort[0]), 
      new VictorSP(DrivetrainConstants.motorRightPort[1])
    );

    this.motorsLeft = new MotorControllerGroup(
      new VictorSP(DrivetrainConstants.motorLeftPort[0]), 
      new VictorSP(DrivetrainConstants.motorLeftPort[1])
    );


    this.setMotorsInverted(
      DrivetrainConstants.isMotorsInverted[0], 
      DrivetrainConstants.isMotorsInverted[1]
    );


    this.drive = new DifferentialDrive(this.motorsRight, this.motorsLeft);

    this.encoderLeft = new Encoder(
      DrivetrainConstants.encoderLeftPort[0],
      DrivetrainConstants.encoderLeftPort[1],
      DrivetrainConstants.isEcondersInverted[0]);


    this.encoderRight = new Encoder(
      DrivetrainConstants.encoderRightPort[0],
      DrivetrainConstants.encoderRightPort[1],
      DrivetrainConstants.isEcondersInverted[1]
    );

      this.setEncodersDistancePerPulse();
      this.resetEncoders();
  
}


    private void resetEncoders() { 
      this.encoderLeft.reset();
      this.encoderRight.reset();
    }


    private void setEncodersDistancePerPulse() {
    }
    
    
    public void reset() {
      this.resetEncoders();
  }


    public Encoder getEncoderLeft() {
      return this.encoderLeft;
  }

    public Encoder getEncoderRight() {
      return this.encoderRight;
  }

    private void setMotorsInverted(boolean b, boolean c){

  }
      
}