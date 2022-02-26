package frc.robot.commands.autonsEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Drivetrain.Arcade;

public class DriveFowardEncoders extends CommandBase {

  private static final double INCREASE_SPEED_RATE = 0.002;
  private static final double METERS_RAN_TO_INCREASE_SPEED = 1.5;

  private static final double METERS_LEFT_TO_DECREASE_SPEED = 1.5;
  private static final double DECREASE_SPEED_RATE = 0.002;

  private static final double MINIMUM_SPEED = 0.45;

  private final Drivetrain drivetrain;
  private double meters;
  private double speed;

  private double startSpeed;

  public DriveFowardEncoders(double meters, double speed, Drivetrain drivetrain) {
    this.meters = meters;
    this.speed = speed;
    this.drivetrain = drivetrain;
    
    this.startSpeed = MINIMUM_SPEED;

    addRequirements(this.drivetrain);
  }

  @Override
  public void initialize() {
    this.drivetrain.reset();
  }

  @Override
  public void execute() {
    if (this.isStarting()) {
      var speedStarting = this.isStartSpeedAtMaximum() ? this.keep() : this.increase();

      this.drivetrain.arcadeDrive(speedStarting, Arcade.noRotation());

    } else if (this.isNearEnd()) {
      var speedNearEnd = this.isSpeedAtMinimum() ? this.keep() : this.decrease();

      this.drivetrain.arcadeDrive(speedNearEnd, Arcade.noRotation());
    } else {
      this.drivetrain.arcadeDrive(this.speed, Arcade.noRotation());
    }
  }

  private boolean isStarting() {
    return this.drivetrain.getAverageDistance() < METERS_RAN_TO_INCREASE_SPEED;
  }

  private boolean isNearEnd() {
    return this.drivetrain.getAverageDistance() + METERS_LEFT_TO_DECREASE_SPEED > this.meters;
  }

  private boolean isStartSpeedAtMaximum() {
    return this.startSpeed >= speed;
  }

  private boolean isSpeedAtMinimum() {
    return this.speed <= MINIMUM_SPEED;
  }

  private double keep() {
    return this.speed;
  }

  private double increase() {
    return this.startSpeed += INCREASE_SPEED_RATE;
  }

  private double decrease() {
    return this.speed -= DECREASE_SPEED_RATE;
  }

  @Override
  public void end(boolean interrupted) {
    this.drivetrain.arcadeDrive(Arcade.stop(), Arcade.noRotation());
    this.drivetrain.reset();
  }

  @Override
  public boolean isFinished() {
    return this.drivetrain.getAverageDistance() > this.meters;
  }
}