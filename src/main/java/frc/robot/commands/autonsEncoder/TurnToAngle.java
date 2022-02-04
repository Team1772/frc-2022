package frc.robot.commands.autonsEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Drivetrain.Arcade;

public class TurnToAngle extends CommandBase {
  private final Drivetrain drivetrain;
  private double angle;
  private double rotationRate;

  public TurnToAngle(double angle, Drivetrain drivetrain) {
    this.angle = angle;
    this.drivetrain = drivetrain;
    this.rotationRate = 0.25;

    addRequirements(this.drivetrain);
  }

  @Override
  public void initialize() {
    this.drivetrain.reset();
  }

  @Override
  public void execute() {
    if(this.needTurnLeft()) {
      this.drivetrain.arcadeDrive(Arcade.noRotation(), this.rotationRate);
    } else {
      this.drivetrain.arcadeDrive(this.rotationRate, Arcade.noRotation());
    }
  }

  private boolean needTurnLeft() {
    return this.angle > 180.0;
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
}