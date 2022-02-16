package frc.robot.commands.drivetrain.autonsTimer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Drivetrain.Arcade;

public class RotateToAngle extends CommandBase {

  private static final double ANGLE_180 = 180.0;
  private static final double ROTATION_RATE = 0.25;

  private final Drivetrain drivetrain;
  private double angle;

  public RotateToAngle(double angle, Drivetrain drivetrain) {
    this.angle = angle;
    this.drivetrain = drivetrain;

    addRequirements(this.drivetrain);
  }

  @Override
  public void initialize() {
    this.drivetrain.reset();
  }

  @Override
  public void execute() {
    this.rotateToAngle();
  }

  private void rotateToAngle() {
    var rotation = this.isRotateLeft() ? negate(ROTATION_RATE) : ROTATION_RATE;

    this.drivetrain.arcadeDrive(Arcade.stop(), rotation);
  }

  private boolean isRotateLeft() {
    return this.angle > ANGLE_180;
  }

  @Override
  public void end(boolean interrupted) {
    this.drivetrain.arcadeDrive(Arcade.stop(), Arcade.noRotation());
    this.drivetrain.reset();
  }

  @Override
  public boolean isFinished() {
    return (int) this.drivetrain.getAngle() == (int) this.angle;
  }

  private double negate(double value) {
    return -value;
  }
}