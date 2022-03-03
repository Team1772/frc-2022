package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.buffer.ForwardFeed;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;

public class SmartCollect extends ParallelCommandGroup {
  private Intake intake;
  private Buffer buffer;

  public SmartCollect(Intake intake, Buffer buffer) {
    this.intake = intake;
    this.buffer = buffer;

    super.addCommands(
      new CollectCargo(intake),
      new ForwardFeed(buffer)
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