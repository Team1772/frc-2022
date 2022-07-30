package frc.robot.commands.shooter;

import frc.core.util.NumberUtil;
import frc.robot.subsystems.Shooter;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class PrepareShootDefault extends CommandBase {
  private final Shooter shooter;
  private DoubleSupplier speed;

  public PrepareShootDefault(DoubleSupplier speed, Shooter shooter) {
    this.shooter = shooter;
    this.speed = speed;

    addRequirements(this.shooter);
  }

  @Override
  public void execute() {
    if (NumberUtil.module(this.speed.getAsDouble()) > 0.3)
        this.shooter.setVelocityMetersPerSecond(25);
    else
        this.shooter.stop();
  }
}
