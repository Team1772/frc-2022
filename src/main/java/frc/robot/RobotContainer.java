package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.core.util.TrajectoryBuilder;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  private Drivetrain drivetrain;
  
  private TrajectoryBuilder trajectoryBuilder;


  public RobotContainer() {
    this.drivetrain = new Drivetrain();

    this.trajectoryBuilder = new TrajectoryBuilder(
      this.drivetrain
    );

    configureButtonBindings();
  }

  private void configureButtonBindings() {}

  public Command getAutonomousCommand() {
    return null;
  }
}
