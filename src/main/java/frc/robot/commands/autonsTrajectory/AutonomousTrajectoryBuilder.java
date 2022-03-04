package frc.robot.commands.autonsTrajectory;

import java.util.HashMap;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;
import frc.robot.commands.shooter.ShootAutonomous;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutonomousTrajectoryBuilder extends SequentialCommandGroup {
  public AutonomousTrajectoryBuilder(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter,
      TrajectoryBuilder trajectoryBuilder) {
    // var currentTrajectories = new HashMap<Integer, String>();
    // currentTrajectories.put(1, "reverse");
    // currentTrajectories.put(2, "straight");
    // trajectoryBuilder.addCurrentTrajectories(currentTrajectories);

    super.addCommands(
        trajectoryBuilder.build(true, "straight")
    );
  }
}
