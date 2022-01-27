package frc.robot.commands;

import frc.robot.subsystems.Hangar;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class HangUp extends CommandBase {
  private final Hangar hanger;

  public HangUp(Hangar hanger) {
    this.hanger = hanger;

    addRequirements(hanger);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    this.hanger.set(1);
  }

  @Override
  public void end(boolean interrupted) {
    this.hanger.set(0);
  }

}
