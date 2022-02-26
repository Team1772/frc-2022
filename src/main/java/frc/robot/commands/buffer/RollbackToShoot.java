package frc.robot.commands.buffer;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.intake.ReleaseCargo;
import frc.robot.commands.shooter.PullCargo;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;


public class RollbackToShoot extends ParallelCommandGroup {
  private Intake intake;
  private Buffer buffer;
  private Shooter shooter;

  public RollbackToShoot(Intake intake, Buffer buffer, Shooter shooter) {
    this.intake = intake;
    this.buffer = buffer;
    this.shooter = shooter;

    super.addCommands(
      // new ReleaseCargo(intake),
      new ReleaseFeed(buffer),
      new PullCargo(shooter)
    );

    super.addRequirements(this.intake, this.buffer, this.shooter);
  }

  @Override
  public boolean isFinished() {
    return this.isShooterSafeToStart();
  }

  public boolean isShooterSafeToStart() {
    return this.buffer.isAllInfraredsOff();
  }

  @Override
  public void end(boolean isInterrupted) {
    this.intake.stop();
    this.buffer.stop();
    this.shooter.stop();
  }
}