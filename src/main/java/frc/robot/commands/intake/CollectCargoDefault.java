package frc.robot.commands.intake;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.util.NumberUtil;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Intake;

public class CollectCargoDefault extends CommandBase {
  private final Intake intake;
  private DoubleSupplier speed;

  public CollectCargoDefault(DoubleSupplier speed, Intake intake) {
    this.intake = intake;
    this.speed = speed;

    addRequirements(this.intake);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (NumberUtil.module(this.speed.getAsDouble()) > 0.3) 
        this.intake.set(IntakeConstants.speed);
    else 
        this.intake.set(0);

  }
}
