package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.util.NumberUtil;
import frc.robot.subsystems.Intake;

public class ReleaseCargo extends CommandBase {
  private final Intake intake;
  private double speed;

  public ReleaseCargo(double speed, Intake intake) {
    this.intake = intake;
    this.speed = speed;

    addRequirements(this.intake);
  }

  @Override
  public void execute() {
      this.intake.set(-0.5);
  }

  @Override
  public void end(boolean isInterrupted) {
    this.intake.stop();
  }
}
