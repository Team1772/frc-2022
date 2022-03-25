package frc.robot.commands.shooter;

import frc.core.util.NumberUtil;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PullCargo extends CommandBase {
  private final Shooter shooter;
  private double speed;
  
  public PullCargo(double speed, Shooter shooter) {
    this.shooter = shooter;
    this.speed = speed;
    
    addRequirements(this.shooter);
  }

  @Override
  public void execute() {
      this.shooter.set(NumberUtil.invert(speed * 0.6));
  }

  @Override
  public boolean isFinished() {
    return !this.shooter.isShooterMoving();
  }

  @Override
  public void end(boolean interrupted) {
    this.shooter.stop();
  }
}
