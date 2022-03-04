package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutonomousTrajectoryBuilder extends SequentialCommandGroup {
  public AutonomousTrajectoryBuilder(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter, TrajectoryBuilder trajectoryBuilder) {
    // Command exitTarmac = trajectoryBuilder.build(true, "exit-tarmac-1");
    // Command reverse = trajectoryBuilder.build(false, "reverse");
    
    super.addCommands(
        // exitTarmac
        // reverse
    );

  }
}
