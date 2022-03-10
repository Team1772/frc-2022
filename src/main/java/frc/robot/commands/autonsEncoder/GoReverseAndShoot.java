package frc.robot.commands.autonsEncoder;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.shooter.RollbackAndShootAutonomous;
import frc.robot.commands.shooter.ShootAutonomous;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class GoReverseAndShoot extends SequentialCommandGroup {
  public GoReverseAndShoot(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter) {

    super.addCommands(
        new DriveReverseEncoders(0.4, 0.8, drivetrain),
        new WaitCommand(1),
        new RollbackAndShootAutonomous(3.5, 25, intake, buffer, shooter)
    );
  }
}
