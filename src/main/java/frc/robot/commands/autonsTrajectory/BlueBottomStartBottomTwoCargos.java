package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.core.util.TrajectoryBuilder;
import frc.robot.commands.intake.CollectCargo;
import frc.robot.commands.intake.CollectCargoTimer;
import frc.robot.commands.intake.SmartCollect;
import frc.robot.commands.intake.SmartCollectTimer;
import frc.robot.commands.shooter.RollbackAndShootAutonomous;
import frc.robot.commands.shooter.ShootAutonomous;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

//max velocity: 3
//max acceleration: 3

public class BlueBottomStartBottomTwoCargos extends SequentialCommandGroup {
  public BlueBottomStartBottomTwoCargos(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter, TrajectoryBuilder trajectoryBuilder) {

    super.addCommands(
    new ParallelCommandGroup(
      trajectoryBuilder.build(true, "alignCargoAndGet4"),
      new SmartCollectTimer(2.5, intake, buffer, shooter)
    ),

    trajectoryBuilder.build(false, "reverseAlignAndStopToShoot4")

    //do not test since there is a light on shoot trajectory
    //new RollbackAndShootAutonomous(2, 25, intake, buffer, shooter)

    );
  }
}
