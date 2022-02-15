package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.core.util.TrajectoryBuilder;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.autonsTrajectory.Test;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  private Drivetrain drivetrain;
  
  private TrajectoryBuilder trajectoryBuilder;

  private XboxController driver;

  public RobotContainer() {
    this.drivetrain = new Drivetrain();
    this.driver = new XboxController(OIConstants.driverControllerPort);

    this.trajectoryBuilder = new TrajectoryBuilder(
      this.drivetrain,
      "testAuto1",
      "testAuto2"
    );

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
    return new Test(this.trajectoryBuilder);
  }

  public void reset() {
    this.drivetrain.reset();
  }
}
