package frc.robot.commands.buffer;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.util.NumberUtil;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;


public class Rollback extends CommandBase {
  private Intake intake;
  private Buffer buffer;
  private Shooter shooter;

  private DoubleSupplier speed;

  public Rollback(DoubleSupplier speed, Intake intake, Buffer buffer, Shooter shooter) {
    this.intake = intake;
    this.buffer = buffer;
    this.shooter = shooter;

    this.speed = speed;
    super.addRequirements(this.intake, this.buffer, this.shooter);
  }

  @Override
  public void execute() {
    this.intake.set(NumberUtil.invert(this.speed.getAsDouble()));
    this.buffer.set(NumberUtil.invert(this.speed.getAsDouble()));
  	this.shooter.set(NumberUtil.invert(this.speed.getAsDouble() * 0.6));
  }

  @Override
  public void end(boolean isInterruptable) {
    this.intake.stop();
    this.buffer.stop();
    this.shooter.stop();
  }
}