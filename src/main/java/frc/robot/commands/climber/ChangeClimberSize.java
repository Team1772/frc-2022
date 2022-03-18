package frc.robot.commands.climber;

import frc.robot.subsystems.Climber;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChangeClimberSize extends CommandBase {
  private Climber climber;
  private DoubleSupplier speed;

  public ChangeClimberSize(DoubleSupplier speed, Climber climber) {
    this.climber = climber;
    this.speed = speed;

    addRequirements(this.climber);
  }

  @Override
  public void execute() {
    this.climber.set(this.speed.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {
    this.climber.stop();
  }
}