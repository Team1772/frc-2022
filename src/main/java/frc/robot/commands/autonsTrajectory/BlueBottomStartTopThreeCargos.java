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

public class BlueBottomStartTopThreeCargos extends SequentialCommandGroup {
  public BlueBottomStartTopThreeCargos(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter, TrajectoryBuilder trajectoryBuilder) {

    super.addCommands(
    new ParallelCommandGroup(
      trajectoryBuilder.build(true, "exitTarmac2"),
      new SmartCollectTimer(1.8, intake, buffer, shooter)
    ),

    trajectoryBuilder.build(false, "reverseAlignAndStopToShoot2"),
    new RollbackAndShootAutonomous(3.5, 25, intake, buffer, shooter),
    trajectoryBuilder.build(false, "reverseAlignCargo2"),
    new ParallelCommandGroup(
      trajectoryBuilder.build(false, "getCargo2"),
      new CollectCargoTimer(1.5, intake)
    ),
    new ParallelCommandGroup(
      trajectoryBuilder.build(false, "alignAndStopToShoot2"),
      new CollectCargoTimer(1.3, intake)
    ),
    new ShootAutonomous(2, intake, buffer, shooter)
    );
  }
}
