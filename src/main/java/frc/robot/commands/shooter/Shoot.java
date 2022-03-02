package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.buffer.RollbackToShoot;
import frc.robot.commands.buffer.UseBufferToShoot;
import frc.robot.commands.intake.SmartFeed;
import frc.robot.commands.intake.UseIntakeToShoot;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Shoot extends ParallelCommandGroup {
    private Intake intake;
    private Buffer buffer;
    private Shooter shooter;
  
    public Shoot(Intake intake, Buffer buffer, Shooter shooter) {
      this.intake = intake;
      this.buffer = buffer;
      this.shooter = shooter;
  
      super.addCommands(
          new ShootCargo(this.shooter),
          new UseBufferToShoot(this.buffer, this.shooter),
          new UseIntakeToShoot(this.intake, this.shooter)
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
