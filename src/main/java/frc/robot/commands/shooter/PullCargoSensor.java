package frc.robot.commands.shooter;

import frc.core.util.NumberUtil;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PullCargoSensor extends CommandBase {
  private final Shooter shooter;
  private final Buffer buffer;
  
  private double speed;

  public PullCargoSensor(Shooter shooter, Buffer buffer) {
    this.shooter = shooter;
    this.buffer = buffer;
    this.speed = 0.27;
    
    addRequirements(this.shooter);
  }

  public PullCargoSensor(double speed, Shooter shooter, Buffer buffer) {
    this.shooter = shooter;
    this.buffer = buffer;
    
    addRequirements(this.shooter);
  }

  @Override
  public void execute() {
    if(this.buffer.isAnyInfraredOn()){
      this.shooter.set(NumberUtil.invert(this.speed));
    }
  }

  @Override
  public void end(boolean interrupted) {
    this.shooter.stop();
  }
}
