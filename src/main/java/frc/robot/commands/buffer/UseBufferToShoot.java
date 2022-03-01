package frc.robot.commands.buffer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class UseBufferToShoot extends CommandBase{
    private final Buffer buffer;
    private final Shooter shooter;

    public UseBufferToShoot(Buffer buffer, Shooter shooter) {
      this.buffer = buffer;
      this.shooter = shooter;
  
      addRequirements(this.buffer);
    }
  
    @Override
    public void execute() {
      if(this.shooter.atSettedVelocity()) {
        this.buffer.set(ShooterConstants.speed);
      }
    }
  
    @Override
    public void end(boolean isInterrupted) {
      this.buffer.stop();
    }
}
