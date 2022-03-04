package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.buffer.FowardFeedSensorAndTimer;
import frc.robot.commands.intake.CollectCargoTimer;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ShootAutonomous extends ParallelCommandGroup {
    private Intake intake;
    private Buffer buffer;
    private Shooter shooter;

    public ShootAutonomous(double secondsEnabled, Intake intake, Buffer buffer, Shooter shooter) {
      this.intake = intake;
      this.buffer = buffer;
      this.shooter = shooter; 

      super.addCommands(
          new ShootTimer(this.shooter, secondsEnabled),
          new FowardFeedSensorAndTimer(this.buffer, this.shooter, secondsEnabled),
          new CollectCargoTimer( secondsEnabled, this.intake)
      );
  
      super.addRequirements(this.intake, this.buffer, this.shooter);
    }
}
