package frc.robot.commands.autonsEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Drivetrain.Arcade;

public class DriveReverseEncoders extends CommandBase {
  private final Drivetrain drivetrain;
  private double meters;
  private double speed;

  public DriveReverseEncoders(double meters, double speed, Drivetrain drivetrain) {
    this.meters = meters;
    this.speed = speed;
    this.drivetrain = drivetrain;

    addRequirements(this.drivetrain);
  }

  @Override
  public void initialize() {
    this.drivetrain.reset();
  }

  @Override
  public void execute() {
    if (this.isNearEnd()) {
      var speedNearEnd =  this.isSpeedAtMinimum() ? this.keep() : this.decrease();

      this.drivetrain.arcadeDrive(speedNearEnd, Arcade.noRotation());
    } else {
      this.drivetrain.arcadeDrive(this.speed, Arcade.noRotation());
    }
  }

  private boolean isNearEnd() {
    var oneMeter = 1.0;

    return abs(this.drivetrain.getAverageDistance()) + oneMeter > this.meters;
  }

  private boolean isSpeedAtMinimum() {
    final var minimum = 0.25;

    return this.speed <= minimum;
  }

  private double keep() {
    return this.speed;
  }

  private double decrease() {
    final var decrease = 0.01;

    return this.speed - decrease;
  }

  @Override
  public void end(boolean interrupted) {
    this.drivetrain.arcadeDrive(Arcade.fullSpeed(), Arcade.noRotation());
    this.drivetrain.reset();
  }

  @Override
  public boolean isFinished() {
    return abs(this.drivetrain.getAverageDistance()) > this.meters;
  }

  private double abs(double value) {
    return Math.abs(value);
  }
}