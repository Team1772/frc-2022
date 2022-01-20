package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.PullCargo;
import frc.robot.commands.ShootCargo;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Shooter shooter;
  private final XboxController operator;

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  public RobotContainer() {
    this.shooter = new Shooter();
    this.operator = new XboxController(0);
   
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    configureButtonBindingsShooter();
  }

  private void configureButtonBindingsShooter(){
    var axisTriggerRight = new JoystickButton(this.operator, Axis.kRightTrigger.value);
    var buttonBumperRight = new JoystickButton(this.operator, Button.kRightBumper.value);
    
    axisTriggerRight
      .whileHeld(new ShootCargo(this.shooter));

    buttonBumperRight
      .whileHeld(new PullCargo(this.shooter));
  }

  public Command getAutonomousCommand() {
    return m_autoCommand;
  }
}
