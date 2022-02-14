package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.components.Limelight;
import frc.core.components.Limelight.LedMode;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.Drivetrain;

public class AimAndRangeTarget extends CommandBase {

  private static final int TARGET = 1;
  private static final double NO_SPEED = 0.0;
  private static final double RESET_STEERING = 0.0;

  private Drivetrain drivetrain;
  private double X_axis;
  private double Y_axis;
  private double steeringAdjust;
  private double distanceAdjust;
  private double headingError;
  private double distanceError;
  private double leftSpeed;
  private double rightSpeed;

  public AimAndRangeTarget(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;

    this.X_axis = Limelight.getX();
    this.steeringAdjust = RESET_STEERING;
    this.distanceAdjust = LimelightConstants.AimAndRangeTarget.kPDistance * distanceError;

    this.headingError = negate(this.X_axis);
    this.distanceError = negate(this.Y_axis);

    this.leftSpeed = NO_SPEED;
    this.rightSpeed = NO_SPEED;
    
    addRequirements(this.drivetrain);
  }

  @Override
  public void initialize() {
    Limelight.setLed(LedMode.ON);
    Limelight.setPipeline(LimelightConstants.pipeline);
  }

  @Override
  public void execute() {
    this.resetSteeringAdjust();

    this.aimTarget();
    this.rangeTarget();

    this.steerToTarget();
  }

  private void resetSteeringAdjust() {
    this.steeringAdjust = RESET_STEERING;
  }

  private void aimTarget() {
    if (this.isSteerLeft()) {
      steeringAdjust =  LimelightConstants.AimAndRangeTarget.kP *
                        headingError -
                        LimelightConstants.AimAndRangeTarget.minCommand;
    } else if (this.isSteerRight()) {
      steeringAdjust =  LimelightConstants.AimAndRangeTarget.kP *
                        headingError +
                        LimelightConstants.AimAndRangeTarget.minCommand;
    }
  }

  private void rangeTarget() {
    this.distanceAdjust = LimelightConstants.AimAndRangeTarget.kPDistance * distanceError;
  }

  private boolean isSteerLeft() {
    return this.X_axis > TARGET;
  }

  private boolean isSteerRight() {
    return this.X_axis < TARGET;
  }

  private void steerToTarget() {
    this.resetSteeringSpeed();

    this.leftSpeed += this.steeringAdjust + distanceAdjust;
    this.rightSpeed -= this.steeringAdjust + distanceAdjust;

    this.drivetrain.tankDrive(this.leftSpeed, this.rightSpeed);
  }

  private void resetSteeringSpeed() {
    this.leftSpeed = NO_SPEED;
    this.rightSpeed = NO_SPEED;
  }

  private double negate(double value) {
    return -value;
  }

  @Override
  public void end(boolean interrupted) {
    Limelight.setLed(LedMode.OFF);
  }
}
