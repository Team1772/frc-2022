package frc.robot.commands.shooter;

import frc.core.util.NumberUtil;
import frc.robot.Constants.ShooterConstants;
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
    this.shooter.setSpeed(NumberUtil.invert(0.5));
  }

  @Override
  public void end(boolean interrupted) {
    this.shooter.stop();
  }
}

