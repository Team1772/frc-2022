package frc.robot.commands;

import frc.robot.subsystems.Hanger;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class HangerDown extends CommandBase {
  private final Hanger hanger;

  public HangerDown(Hanger hanger) {
    this.hanger = hanger;

    addRequirements(hanger);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    this.hanger.set(-1, -1);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
