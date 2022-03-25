package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.components.Limelight;
import frc.core.components.Limelight.LedMode;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.Drivetrain;

public class Seeking extends CommandBase {

  private static final double NO_SPEED = 0.0;
  private static final double RESET_STEERING = 0.0;
  private static final double STEERING_RATE = 0.3;

  private Drivetrain drivetrain;
  private double X_axis;
  private double steeringAdjust;
  private double headingError;
  private double leftSpeed;
  private double rightSpeed;


  public Seeking(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;

    this.X_axis = Limelight.getX();
    this.steeringAdjust = RESET_STEERING;

    this.headingError = this.X_axis;

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

    if (!Limelight.isTarget()) this.seekTarget();
    else this.maintainOnTarget();

    this.steerToTarget();
  }

  private void resetSteeringAdjust() {
    this.steeringAdjust = RESET_STEERING;
  }

  private void seekTarget() {
    this.steeringAdjust = STEERING_RATE;
  }

  private void maintainOnTarget() {
    this.steeringAdjust = LimelightConstants.Seeking.kP * this.headingError;
  }

  private void steerToTarget() {
    this.resetSteeringSpeed();

    this.leftSpeed += this.steeringAdjust;
    this.rightSpeed -= this.steeringAdjust;

    this.drivetrain.tankDrive(this.leftSpeed, this.rightSpeed);
  }

  private void resetSteeringSpeed() {
    this.leftSpeed = NO_SPEED;
    this.rightSpeed = NO_SPEED;
  }

  @Override
  public void end(boolean interrupted) {
    Limelight.setLed(LedMode.OFF);
  }
}