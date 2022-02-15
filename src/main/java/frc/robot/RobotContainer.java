package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class RobotContainer {
  private final Intake intake;

  public RobotContainer() {
    this.intake = new Intake();
    this.configureButtonBindings();
  }

  private void configureButtonBindings() {
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
