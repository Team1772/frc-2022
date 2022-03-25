package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.buffer.UseBufferToShoot;
import frc.robot.commands.intake.CollectCargoSensor;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Shoot extends ParallelCommandGroup {
    private Intake intake;
    private Buffer buffer;
    private Shooter shooter;

    private double speed;
  
    public Shoot(double speed, Intake intake, Buffer buffer, Shooter shooter) {
      this.intake = intake;
      this.buffer = buffer;
      this.shooter = shooter;
      this.speed = speed;
  
      super.addCommands(
          new ShootCargo(this.speed, this.shooter),
          new UseBufferToShoot(this.buffer, this.shooter),
          new CollectCargoSensor(this.intake, this.shooter)
      );
  
      super.addRequirements(this.intake, this.buffer, this.shooter);
    }
  
    @Override
    public void end(boolean isInterrupted) {
      this.intake.stop();
      this.buffer.stop();
      this.shooter.stop();
    }
}
