package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class Straight extends SequentialCommandGroup {
  public Straight(TrajectoryBuilder trajectoryBuilder) {
    Command straight = trajectoryBuilder.build(true, "straight");
    
    super.addCommands(
        straight
    );

  }
}
