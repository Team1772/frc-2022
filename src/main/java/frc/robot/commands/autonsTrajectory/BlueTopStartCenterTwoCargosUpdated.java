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
import frc.robot.commands.shooter.ShootAutonomous;
import frc.robot.commands.util.WaitSeconds;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

//max velocity: 3
//max acceleration: 3

public class BlueTopStartCenterTwoCargosUpdated extends SequentialCommandGroup {
  public BlueTopStartCenterTwoCargosUpdated(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter, TrajectoryBuilder trajectoryBuilder) {

    super.addCommands(
      new ParallelCommandGroup(
        trajectoryBuilder.build(true, "exitTarmac5"),
        new ReleaseFeedTimer(1.5, buffer)
      ),
      new ShootAutonomous(2, intake, buffer, shooter),

      trajectoryBuilder.build(false, "reverseAlignCargo5"),

      new ParallelCommandGroup(
        trajectoryBuilder.build(false, "getCargoAndStopToShoot5"),
        new CollectCargoTimer(3, intake)
      ),
       
      new ShootAutonomous(2, intake, buffer, shooter)
    );
  }
}
