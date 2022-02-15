package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ShootCargo;
import frc.robot.subsystems.Shooter;

public class RobotContainer {

  private final Shooter shooter;

  private XboxController driver;
  public RobotContainer() {
    this.shooter = new Shooter();
    this.driver = new XboxController(0);
    
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    var buttonA = new JoystickButton(this.driver, Button.kA.value);

    buttonA.whileHeld(new ShootCargo(this.shooter));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
