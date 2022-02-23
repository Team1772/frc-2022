package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Intake;

public class SmartIntake extends ParallelCommandGroup {
  public SmartIntake(Intake intake) {
    super.addCommands(
      new CollectCargo(intake),
      new RejectOtherAllianceCargo(intake)
    );
  }
}
