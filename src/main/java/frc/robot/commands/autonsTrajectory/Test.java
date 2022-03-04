package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;
import frc.robot.commands.buffer.ForwardFeed;
import frc.robot.subsystems.Buffer;

public class Test extends ParallelCommandGroup {

  public Test(TrajectoryBuilder trajectoryBuilder, Buffer buffer) {
    super.addCommands(
      trajectoryBuilder.run("testAuto1", "testAuto2"),
      new ForwardFeed(buffer)
    );
  }
}