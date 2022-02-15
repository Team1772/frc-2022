package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.autonsEncoder.AutonomousEncoders;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  private final Drivetrain drivetrain;

  private XboxController driver;

  public RobotContainer() {
    this.drivetrain = new Drivetrain();
    this.driver = new XboxController(0);
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    this.drivetrain.setDefaultCommand(
      new ArcadeDrive(
        this.drivetrain, 
        () -> this.driver.getLeftY(), 
        () -> this.driver.getRightX()
      )
    );
  }

  public Command getAutonomousCommand() {
    return new AutonomousEncoders(this.drivetrain);
  }
}
