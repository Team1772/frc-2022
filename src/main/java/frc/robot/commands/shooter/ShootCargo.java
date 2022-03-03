package frc.robot.commands.shooter;

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
    if(this.shooter.isSensorVelocityPositive()) {
      this.shooter.setVelocityMetersPerSecond(25);
    }
  }

  @Override
  public void end(boolean interrupted) {
    this.shooter.stop();
  }
}
