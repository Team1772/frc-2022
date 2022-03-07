package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.buffer.ForwardFeed;
import frc.robot.commands.buffer.ForwardFeedTimer;
import frc.robot.commands.shooter.PullCargoUntilShooterHasNoVelocity;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class SmartCollectTimer extends ParallelCommandGroup {
  private Intake intake;
  private Buffer buffer;

  private double secondsEnabled;

  public SmartCollectTimer(double secondsEnabled, Intake intake, Buffer buffer, Shooter shooter) {
    this.intake = intake;
    this.buffer = buffer;
    this.secondsEnabled = secondsEnabled;

    super.addCommands(
      new CollectCargoTimer(this.secondsEnabled, intake),
      new ForwardFeedTimer(this.secondsEnabled, buffer),
      new PullCargoUntilShooterHasNoVelocity(shooter)
    );

    super.addRequirements(this.intake, this.buffer);
  }

  @Override
  public void end(boolean isInterrupted) {
    this.intake.stop();
    this.buffer.stop();
  }
}