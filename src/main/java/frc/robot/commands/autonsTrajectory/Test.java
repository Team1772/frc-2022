package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;

public class Test extends SequentialCommandGroup {

  public Test(TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.run("testAuto1", "testAuto2")
    );
  }
}