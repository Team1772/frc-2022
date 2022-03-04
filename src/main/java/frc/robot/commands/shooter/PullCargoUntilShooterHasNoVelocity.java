package frc.robot.commands.shooter;

import frc.core.util.NumberUtil;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PullCargoUntilShooterHasNoVelocity extends CommandBase {
  private final Shooter shooter;
  
  public PullCargoUntilShooterHasNoVelocity(Shooter shooter) {
    this.shooter = shooter;
    
    addRequirements(this.shooter);
  }

  @Override
  public void execute() {
    if (this.shooter.isShooterMoving()){
      this.shooter.set(NumberUtil.invert(0.08));
    }
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
