package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.HangDown;
import frc.robot.commands.HangUp;
import frc.robot.subsystems.Hangar;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  private Hangar hanger;
  private XboxController operator;

  public RobotContainer() {
    this.hanger = new Hangar();
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    this.configureButtonBindingsHanger();
  }

  private void configureButtonBindingsHanger() {
    var buttonA = new JoystickButton(this.operator, Button.kA.value);
    var buttonB = new JoystickButton(this.operator, Button.kB.value);

    buttonA
      .whileHeld(new HangDown(this.hanger));
    
    buttonB
      .whileHeld(new HangUp(this.hanger));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
