package frc.robot.commands.autonsEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Drivetrain.Arcade;

public class Test extends CommandBase {
  
  private static final double DECREASE_SPEED_RATE = 0.01;
  private static final double MINIMUM_SPEED = 0.25;
  private static final double ONE_METER = 1.0;

  private final Drivetrain drivetrain;
  private double meters;
  private double speed;

  public Test(double meters, double speed, Drivetrain drivetrain) {
    this.meters = meters;
    this.speed = speed;
    this.drivetrain = drivetrain;

    addRequirements(this.drivetrain);
  }

  @Override
  public void initialize() {
    // this.drivetrain.reset();
  }

  @Override
  public void execute() {
    this.drivetrain.arcadeDrive(this.speed, Arcade.noRotation());
  }

  private boolean isNearEnd() {
    return this.drivetrain.getAverageDistance() + ONE_METER > this.meters;
  }

  private boolean isSpeedAtMinimum() {
    return this.speed <= MINIMUM_SPEED;
  }

  private double keep() {
    return this.speed;
  }

  private double decrease() {
    return this.speed - DECREASE_SPEED_RATE;
  }

  @Override
  public void end(boolean interrupted) {
    // this.drivetrain.arcadeDrive(Arcade.stop(), Arcade.noRotation());
    // this.drivetrain.reset();
  }

  @Override
  public boolean isFinished() {
    // return this.drivetrain.getAverageDistance() > this.meters;
    return false;
  }
}