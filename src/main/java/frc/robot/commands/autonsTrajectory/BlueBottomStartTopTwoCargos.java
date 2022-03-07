package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;
import frc.robot.commands.intake.SmartCollect;
import frc.robot.commands.intake.SmartCollectTimer;
import frc.robot.commands.shooter.RollbackAndShootAutonomous;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class BlueBottomStartTopTwoCargos extends SequentialCommandGroup {
  public BlueBottomStartTopTwoCargos(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter, TrajectoryBuilder trajectoryBuilder) {

    super.addCommands(
    new ParallelCommandGroup(
      trajectoryBuilder.build(true, "exitTarmac1"),
      new SmartCollectTimer(2, intake, buffer, shooter)
    )
    // new ParallelCommandGroup(

    // )
    );
  }
}
