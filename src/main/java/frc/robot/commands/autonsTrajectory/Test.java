package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class Test extends ParallelCommandGroup {

  public Test(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.build("test")
    );
  }
}