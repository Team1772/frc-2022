package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.components.SmartNavX;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {

  public enum Arcade {
    FULL_SPEED(1.0), 
    STOP(0.0), 
    NO_ROTATION(0.0);

    public final double value;
    Arcade(double value) {
      this.value = value;
    }

    public double get() {
      return this.value;
    }

    public static double fullSpeed() {
      return FULL_SPEED.get();
    }

    public static double stop() {
      return STOP.get();
    }

    public static double noRotation() {
      return NO_ROTATION.get();
    }
  }

  private final MotorControllerGroup motorsRight, motorsLeft;
  private final DifferentialDrive drive;
  private final Encoder encoderRight, encoderLeft;
  private final SmartNavX navX;

  public Drivetrain() {
    this.motorsRight = new MotorControllerGroup(
        new VictorSP(DrivetrainConstants.motorRightPort[0]),
        new VictorSP(DrivetrainConstants.motorRightPort[1]));
    this.motorsLeft = new MotorControllerGroup(
        new VictorSP(DrivetrainConstants.motorLeftPort[0]),
        new VictorSP(DrivetrainConstants.motorLeftPort[1]));

    this.setMotorsInverted(
        DrivetrainConstants.isMotorsInverted[0],
        DrivetrainConstants.isMotorsInverted[1]);

    this.drive = new DifferentialDrive(this.motorsRight, this.motorsLeft);

    this.encoderLeft = new Encoder(
        DrivetrainConstants.encoderLeftPort[0],
        DrivetrainConstants.encoderLeftPort[1],
        DrivetrainConstants.isEcondersInverted[0]);

    this.encoderRight = new Encoder(
        DrivetrainConstants.encoderRightPort[0],
        DrivetrainConstants.encoderRightPort[1],
        DrivetrainConstants.isEcondersInverted[1]);

    this.navX = new SmartNavX();    

    this.setEncodersDistancePerPulse();
    this.resetEncoders();
  }

  public void arcadeDrive(double forward, double rotation) {
    this.drive.arcadeDrive(forward, -(rotation));
  }

  //use it on autonomous with encoders
  public void setMaxOutput(double maxOutput) {
    this.drive.setMaxOutput(maxOutput);
  }

  public void resetEncoders() {
    this.encoderLeft.reset();
    this.encoderRight.reset();
  }

  public void resetNavX() {
    this.navX.reset();
  }

  public void reset() {
    this.resetNavX();
    this.resetEncoders();
  }

  public double getAngle() {
    return this.navX.getAngle();
  }

  public Rotation2d getRotation2d() {
    return this.navX.getRotation2d();
  }

  public Encoder getEncoderLeft() {
    return this.encoderLeft;
  }

  public Encoder getEncoderRight() {
    return this.encoderRight;
  }

  public double getAverageDistance() {
    return this.encoderLeft.getDistance() + this.encoderRight.getDistance() / 2.0;
  }

  public void setMotorsInverted(boolean isMotorsLeftInverted, boolean isMotorsRightInverted) {
    this.motorsRight.setInverted(isMotorsRightInverted);
    this.motorsLeft.setInverted(isMotorsLeftInverted);
  }

  public void setEncodersDistancePerPulse() {
    var wheelCircumferenceMeters = Units.inchesToMeters(DrivetrainConstants.wheelRadius) * 2 * Math.PI;

    var distancePerPulseLeft = wheelCircumferenceMeters / (double) DrivetrainConstants.pulsesLeft;
    var distancePerPulseRight = wheelCircumferenceMeters / (double) DrivetrainConstants.pulsesRight;

    this.encoderLeft.setDistancePerPulse(distancePerPulseLeft);
    this.encoderRight.setDistancePerPulse(distancePerPulseRight);
  }
}