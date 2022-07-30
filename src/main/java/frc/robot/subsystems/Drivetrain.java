package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.core.components.Limelight;
import frc.core.components.SmartNavX;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
  private Field2d m_field = new Field2d();

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
  private final DifferentialDriveOdometry odometry;
  private EncoderSim m_leftEncoderSim;
  private EncoderSim m_rightEncoderSim;
  DifferentialDrivetrainSim m_driveSim;

  public Drivetrain() {
    SmartDashboard.putData("Field", m_field);
    this.motorsLeft = new MotorControllerGroup(
        new VictorSP(DrivetrainConstants.motorLeftBack),
        new VictorSP(DrivetrainConstants.motorLeftFront));
    this.motorsRight = new MotorControllerGroup(
        new VictorSP(DrivetrainConstants.motorRightBack),
        new VictorSP(DrivetrainConstants.motorRightFront));

    this.setMotorsInverted(
        DrivetrainConstants.isMotorsLeftInverted,
        DrivetrainConstants.isMotorsRightInverted);

    this.drive = new DifferentialDrive(this.motorsRight, this.motorsLeft);

    m_driveSim = DifferentialDrivetrainSim.createKitbotSim(
      KitbotMotor.kDualCIMPerSide, // 2 CIMs per side.
      KitbotGearing.k10p71,        // 10.71:1
      KitbotWheelSize.kSixInch,     // 6" diameter wheels.
      null                         // No measurement noise.
    );

    this.encoderLeft = new Encoder(
        DrivetrainConstants.encoderLeftPortOne,
        DrivetrainConstants.encoderLeftPortTwo,
        DrivetrainConstants.isEncoderLeftInverted);

    this.encoderRight = new Encoder(
        DrivetrainConstants.encoderRightPortOne,
        DrivetrainConstants.encoderRightPortTwo,
        DrivetrainConstants.isEncoderRightInverted);

    m_leftEncoderSim = new EncoderSim(encoderLeft);
    m_rightEncoderSim = new EncoderSim(encoderRight);

    this.navX = new SmartNavX();

    this.odometry = new DifferentialDriveOdometry(this.getRotation2d());
 
    this.setEncodersDistancePerPulse();
    this.resetEncoders();
  }

  public void arcadeDrive(double forward, double rotation) {
    this.drive.arcadeDrive(forward, rotation);
  }

  public void curvatureDrive(double forward, double rotation, boolean isQuickTurn) {
    this.drive.curvatureDrive(forward, -(rotation), isQuickTurn);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    this.drive.tankDrive(leftSpeed, rightSpeed);
  }
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    this.motorsLeft.setVoltage(leftVolts);
    this.motorsRight.setVoltage(rightVolts);

    this.drive.feed();
  }

  public void setMaxOutput(double maxOutput) {
    this.drive.setMaxOutput(maxOutput);
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

  public void resetHeading() {
    
  }

  public Encoder getEncoderLeft() {
    return this.encoderLeft;
  }

  public Encoder getEncoderRight() {
    return this.encoderRight;
  }

  public void setMotorsInverted(boolean isMotorsLeftInverted, boolean isMotorsRightInverted) {
    this.motorsLeft.setInverted(isMotorsLeftInverted);
    this.motorsRight.setInverted(isMotorsRightInverted);
  }

  public void setEncodersDistancePerPulse() {
    var wheelCircumferenceMeters = Units.inchesToMeters(DrivetrainConstants.wheelRadius) * 2 * Math.PI;

    var distancePerPulse = wheelCircumferenceMeters / (double) DrivetrainConstants.pulsesPerRotation;

    this.encoderLeft.setDistancePerPulse(distancePerPulse);
    this.encoderRight.setDistancePerPulse(distancePerPulse);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("[DRIVETRAIN] Encoder Left", this.encoderLeft.get());
    SmartDashboard.putNumber("[DRIVETRAIN] Encoder Right", this.encoderRight.get());
    SmartDashboard.putNumber("[DRIVETRAIN] Average Distance", this.getAverageDistance());

    this.updateOdometry();
    m_field.setRobotPose(getPose());
  }
}