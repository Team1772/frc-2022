package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Intake;

public class CollectCargo extends CommandBase {
  private final Intake intake;

  public CollectCargo(Intake intake) {
    this.intake = intake;

    addRequirements(this.intake);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    this.intake.set(IntakeConstants.speed);
  }

  @Override
  public void end(boolean isInterrupted) {
    this.intake.stop();
  }
}
