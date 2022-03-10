package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.buffer.ForwardFeed;
import frc.robot.commands.shooter.PullCargoUntilShooterHasNoVelocity;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class SmartCollect extends ParallelCommandGroup {
  private Intake intake;
  private Buffer buffer;

  public SmartCollect(Intake intake, Buffer buffer, Shooter shooter) {
    this.intake = intake;
    this.buffer = buffer;

    super.addCommands(
      new CollectCargo(intake),
      new ForwardFeed(buffer),
      new PullCargoUntilShooterHasNoVelocity(shooter)
    );

    super.addRequirements(this.intake, this.buffer);
  }

  @Override
  public boolean isFinished() {
    return this.buffer.isInfraredTopOn();
  }

  @Override
  public void end(boolean isInterrupted) {
    this.intake.stop();
    this.buffer.stop();
  }
}