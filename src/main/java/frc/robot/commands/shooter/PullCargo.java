package frc.robot.commands.shooter;

import frc.core.util.NumberUtil;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PullCargo extends CommandBase {
  private final Shooter shooter;
  private final Buffer buffer;

  public PullCargo(Shooter shooter, Buffer buffer) {
    this.shooter = shooter;
    this.buffer = buffer;
    
    addRequirements(this.shooter);
  }

  @Override
  public void execute() {
    if(this.buffer.isAnyInfraredOn()){
      this.shooter.setVelocityMetersPerSecond(NumberUtil.invert(7));
    }
  }

  @Override
  public void end(boolean interrupted) {
    this.shooter.stop();
  }
}
