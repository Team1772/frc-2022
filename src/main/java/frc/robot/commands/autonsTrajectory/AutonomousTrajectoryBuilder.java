package frc.robot.commands.autonsTrajectory;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.core.util.TrajectoryBuilder;
import frc.robot.commands.autonsEncoder.DriveFowardEncoders;
import frc.robot.commands.util.WaitSeconds;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutonomousTrajectoryBuilder extends SequentialCommandGroup {
  public AutonomousTrajectoryBuilder(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter, TrajectoryBuilder trajectoryBuilder) {
    super.addCommands(
      trajectoryBuilder.run("exit-tarmac-1"),
      trajectoryBuilder.run("enter-tarmac-1")
      // new DriveFowardEncoders(0.5, 0.45, drivetrain)
    );
  }
}
