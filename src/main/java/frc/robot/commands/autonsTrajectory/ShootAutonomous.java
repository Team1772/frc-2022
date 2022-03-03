package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.buffer.FowardFeedSensorAndTimer;
import frc.robot.commands.buffer.RollbackToShoot;
import frc.robot.commands.intake.CollectCargoTimer;
import frc.robot.commands.shooter.ShootTimer;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ShootAutonomous extends SequentialCommandGroup {
    private Intake intake;
    private Buffer buffer;
    private Shooter shooter;

    public ShootAutonomous(double secondsEnabled, Intake intake, Buffer buffer, Shooter shooter) {
      this.intake = intake;
      this.buffer = buffer;
      this.shooter = shooter;  
      super.addCommands(
          // new RollbackToShoot(this.intake, this.buffer, this.shooter),

          new ParallelCommandGroup(
            new ShootTimer(this.shooter, secondsEnabled),
            new FowardFeedSensorAndTimer(this.buffer, this.shooter, secondsEnabled),
            new CollectCargoTimer( secondsEnabled, this.intake, this.shooter)
          ) 
      );
  
      super.addRequirements(this.intake, this.buffer, this.shooter);
    }
}
