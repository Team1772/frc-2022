package frc.robot.commands.autonsTrajectory;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.buffer.RollbackToShoot;
import frc.robot.commands.buffer.UseBufferToShoot;
import frc.robot.commands.buffer.UseBufferToShootTimer;
import frc.robot.commands.intake.SmartFeed;
import frc.robot.commands.intake.UseIntakeToShoot;
import frc.robot.commands.intake.UseIntakeToShootTimer;
import frc.robot.commands.shooter.ShootTimer;
import frc.robot.commands.shooter.ShootCargo;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutonomousShoot extends SequentialCommandGroup {
    private Intake intake;
    private Buffer buffer;
    private Shooter shooter;

    private double secondsEnabled;

    public AutonomousShoot(double secondsEnabled, Intake intake, Buffer buffer, Shooter shooter) {
      this.intake = intake;
      this.buffer = buffer;
      this.shooter = shooter;
      this.secondsEnabled = secondsEnabled;
  
      super.addCommands(
          // new RollbackToShoot(this.intake, this.buffer, this.shooter),

          new ParallelCommandGroup(
            new ShootTimer(this.shooter, secondsEnabled),
            new UseBufferToShootTimer(this.buffer, this.shooter, secondsEnabled),
            new UseIntakeToShootTimer(this.intake, this.shooter, secondsEnabled)
          ) 
      );
  
      super.addRequirements(this.intake, this.buffer, this.shooter);
    }
  
    // @Override
    // public void end(boolean isInterrupted) {
    //   this.intake.stop();
    //   this.buffer.stop();
    //   this.shooter.stop();
    // }
}
