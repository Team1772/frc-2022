package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.core.util.TrajectoryBuilder;
import frc.robot.commands.autonsEncoder.DriveReverseEncoders;
import frc.robot.commands.buffer.ForwardFeed;
import frc.robot.commands.buffer.ReleaseFeedTimer;
import frc.robot.commands.intake.CollectCargoTimer;
import frc.robot.commands.intake.ReleaseCargoTimer;
import frc.robot.commands.shooter.RollbackAndShootAutonomous;
import frc.robot.commands.shooter.ShootAutonomous;
import frc.robot.commands.util.WaitSeconds;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

//max velocity: 3
//max acceleration: 3

public class BlueTopStartTopTwoCargos extends SequentialCommandGroup {
  public BlueTopStartTopTwoCargos(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter, TrajectoryBuilder trajectoryBuilder) {

    super.addCommands(
      new ParallelCommandGroup(
        trajectoryBuilder.build(true, "alignCargoAndGet3"),
        new CollectCargoTimer(2.4, intake)
      ),

      trajectoryBuilder.build(false, "reverseAlignAndStopToShoot3"),

      new RollbackAndShootAutonomous(3.5, 25, intake, buffer, shooter)
    );

  }
}