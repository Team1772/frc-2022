package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.HangerDown;
import frc.robot.commands.HangerUp;
import frc.robot.subsystems.Hanger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  private Hanger hanger;
  private XboxController operator;

  public RobotContainer() {
    this.hanger = new Hanger();
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    this.configureButtonBindingsHanger();
  }

  private void configureButtonBindingsHanger() {
    var buttonA = new JoystickButton(this.operator, Button.kA.value);
    var buttonB = new JoystickButton(this.operator, Button.kB.value);

    buttonA
      .whileHeld(new HangerDown(this.hanger));
    
    buttonB
      .whileHeld(new HangerUp(this.hanger));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
