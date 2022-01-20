package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PullCargo extends CommandBase {
  private final Shooter shooter;

  public PullCargo(Shooter shooter) {
    this.shooter = shooter;
    
    addRequirements(this.shooter);
  }

  @Override
  public void execute() {
      this.shooter.setSpeed(-1);
  }

  @Override
  public void end(boolean interrupted) {
    this.shooter.stop();
  }
}

