package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class UseIntakeToShoot extends CommandBase{
    private final Intake intake;
    private final Shooter shooter;

    public UseIntakeToShoot(Intake intake, Shooter shooter) {
      this.intake = intake;
      this.shooter = shooter;
  
      addRequirements(this.intake);
    }
  
    @Override
    public void execute() {
      if(this.shooter.atSettedVelocity()) {
        this.intake.set(ShooterConstants.speed);
      }
    }
  
    @Override
    public void end(boolean isInterrupted) {
      this.intake.stop();
    }
}
