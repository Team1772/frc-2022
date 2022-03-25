package frc.robot.commands.autonsEncoder;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.intake.CollectCargoTimer;
import frc.robot.commands.shooter.RollbackAndShootAutonomous;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class GetCargoAndShoot extends SequentialCommandGroup {
  public GetCargoAndShoot(Drivetrain drivetrain, Intake intake, Buffer buffer, Shooter shooter) {

    super.addCommands(
      new ParallelCommandGroup(
        new DriveFowardEncoders(1.6, 0.8, drivetrain),
        new CollectCargoTimer(3, intake)
      ),

      new WaitCommand(1),
      new RotateToAngle(150, drivetrain),
      new DriveFowardEncoders(0.2, 0.8, drivetrain),
      new WaitCommand(1.5),
      new RollbackAndShootAutonomous(3.5, 25, intake, buffer, shooter)
    );
  }
}
