package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.components.SmartNavX;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
  private final MotorControllerGroup motorsRight, motorsLeft;
  private final DifferentialDrive drive;
  private final Encoder encoderRight, encoderLeft;
  private final SmartNavX navX;
  private final DifferentialDriveOdometry odometry;

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
        DrivetrainConstants.isEncodersInverted[0]);

    this.encoderRight = new Encoder(
        DrivetrainConstants.encoderRightPort[0],
        DrivetrainConstants.encoderRightPort[1],
        DrivetrainConstants.isEncodersInverted[1]);

    this.navX = new SmartNavX();

    this.odometry = new DifferentialDriveOdometry(this.getRotation2d());
 
    this.setEncodersDistancePerPulse();
    this.resetEncoders();
  }

  public void arcadeDrive(double forward, double rotation) {
    this.drive.arcadeDrive(forward, -(rotation));
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    this.motorsLeft.setVoltage(leftVolts);
    this.motorsRight.setVoltage(rightVolts);
    this.drive.feed();
  }

  public void resetEncoders() {
    this.encoderLeft.reset();
    this.encoderRight.reset();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(
      this.encoderLeft.getRate(), 
      this.encoderRight.getRate()
    );
  }

  public double getAverageDistance() {
    var averageDistance = (this.encoderLeft.getDistance() + this.encoderRight.getDistance()) / 2.0;
    
    return averageDistance;
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

  public Pose2d getPose() {
    return this.odometry.getPoseMeters();
  }

  public void updateOdometry() {
    this.odometry.update(
      this.getRotation2d(), 
      this.encoderLeft.getDistance(), 
      this.encoderRight.getDistance()
    );
  }

  public void resetOdometry(Pose2d pose) {
    this.resetEncoders();
    this.odometry.resetPosition(pose, this.getRotation2d());
  }

  public Encoder getEncoderLeft() {
    return this.encoderLeft;
  }

  public Encoder getEncoderRight() {
    return this.encoderRight;
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

  @Override
  public void periodic() {
    SmartDashboard.putNumber("[DRIVETRAIN] Encoder Left", this.encoderLeft.get());
    SmartDashboard.putNumber("[DRIVETRAIN] Encoder Right", this.encoderRight.get());
    SmartDashboard.putNumber("[DRIVETRAIN] Average Distance", this.getAverageDistance());
    
    this.updateOdometry();
  }
}