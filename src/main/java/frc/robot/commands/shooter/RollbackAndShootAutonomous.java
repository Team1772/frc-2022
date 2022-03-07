package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.buffer.ForwardFeedSensorAndTimer;
import frc.robot.commands.buffer.RollbackToShoot;
import frc.robot.commands.intake.CollectCargoTimer;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class RollbackAndShootAutonomous extends SequentialCommandGroup {
    private Intake intake;
    private Buffer buffer;
    private Shooter shooter;

    public RollbackAndShootAutonomous(double secondsShooterEnabled, Intake intake, Buffer buffer, Shooter shooter) {
      this.intake = intake;
      this.buffer = buffer;
      this.shooter = shooter;  
      super.addCommands(
          new RollbackToShoot(this.intake, this.buffer, this.shooter),

          new ParallelCommandGroup(
            new ShootTimer(this.shooter, secondsShooterEnabled),
            new ForwardFeedSensorAndTimer(this.buffer, this.shooter, secondsShooterEnabled),
            new CollectCargoTimer(secondsShooterEnabled, this.intake)
          ) 
      );
  
      super.addRequirements(this.intake, this.buffer, this.shooter);
    }
}
