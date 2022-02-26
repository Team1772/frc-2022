package frc.robot.commands.autonsEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.util.NumberUtil;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Drivetrain.Arcade;

public class RotateToAngle extends CommandBase {

  private static final int ZERO_NUMBER = 0;
  private static final double ROTATION_RATE = 0.63;

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
    var rotation = this.isRotateRight() ? rotateRight() : rotateLeft();
    System.out.println(this.drivetrain.getAngle());
    this.drivetrain.arcadeDrive(Arcade.stop(), rotation);
  }

  private boolean isRotateRight() {
    return this.isAnglePositive();
  }

  private boolean isAnglePositive() {
    return this.angle > ZERO_NUMBER;
  }

  private double rotateRight() {
    return NumberUtil.invert(ROTATION_RATE);
  }

  private double rotateLeft() {
    return ROTATION_RATE;    
  }

  @Override
  public void end(boolean interrupted) {
    this.drivetrain.arcadeDrive(Arcade.stop(), Arcade.noRotation());
    this.drivetrain.reset();
  }

  @Override
  public boolean isFinished() {
    var desiredAngle = isRotateRight() ? 
    this.desiredAngle() >= this.angle : 
    this.desiredAngle() <= this.angle; 

  return desiredAngle;
  }

  private int desiredAngle() {
    return (int) this.drivetrain.getAngle();
  }
}