package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootCargo extends CommandBase {
  private final Shooter shooter;
  private double speed;

  public ShootCargo(double speed, Shooter shooter) {
    this.shooter = shooter;
    this.speed = speed;
    
    addRequirements(this.shooter);
  }

  @Override
  public void execute() {
    if(this.shooter.isSafetyShoot()) {
      this.shooter.setVelocityMetersPerSecond(speed);
    }
  }

  @Override
  public void end(boolean interrupted) {
    this.shooter.stop();
  }
}
