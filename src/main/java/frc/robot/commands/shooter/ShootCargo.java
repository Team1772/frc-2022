package frc.robot.commands.shooter;

import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootCargo extends CommandBase {
  private final Shooter shooter;

  public ShootCargo(Shooter shooter) {
    this.shooter = shooter;
    
    addRequirements(this.shooter);
  }

  @Override
  public void execute() {

    this.shooter.set(ShooterConstants.speed);
  }

  @Override
  public void end(boolean interrupted) {
    this.shooter.stop();
  }
}

