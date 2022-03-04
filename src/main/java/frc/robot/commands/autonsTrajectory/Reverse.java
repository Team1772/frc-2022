package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class Reverse extends SequentialCommandGroup {
  public Reverse(TrajectoryBuilder trajectoryBuilder) {
    Command reverse = trajectoryBuilder.build(true, "reverse");
    
    super.addCommands(
        reverse
    );

  }
}
