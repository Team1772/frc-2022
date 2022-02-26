package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.util.NumberUtil;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Intake;

public class ReleaseCargo extends CommandBase {
  private final Intake intake;

  public ReleaseCargo(Intake intake) {
    this.intake = intake;

    addRequirements(this.intake);
  }

  @Override
  public void execute() {
    this.intake.set(NumberUtil.invert(0.4));
  }

  @Override
  public void end(boolean isInterrupted) {
    this.intake.stop();
  }
}
