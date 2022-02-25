package frc.robot.commands.buffer;

import frc.core.util.NumberUtil;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RollbackToShoot extends CommandBase {

  private static final double INTAKE_SPEED = 0.4;
  private static final double BUFFER_SPEED = 0.35;
  private static final double SHOOTER_SPEED = 0.4;

  private Buffer buffer;
  private Intake intake;
  private Shooter shooter;

  public RollbackToShoot(Buffer buffer, Intake intake, Shooter shooter) {
    this.buffer = buffer;
    this.intake = intake;
    this.shooter = shooter;

    addRequirements(this.buffer, this.intake, this.shooter);
  }

  @Override
  public void execute() {
    if(this.isRollbackToShoot()) {
      this.intake.set(NumberUtil.invert(INTAKE_SPEED));
      this.buffer.set(NumberUtil.invert(BUFFER_SPEED));
      this.shooter.set(NumberUtil.invert(SHOOTER_SPEED));
    }
  }

  private boolean isRollbackToShoot() {
    return this.buffer.isAnyInfraredOn();
  }

  @Override
  public boolean isFinished() {
    return isShooterSafeToRun();
  }

  private boolean isShooterSafeToRun() {
    return this.buffer.isAllInfraredsOff();
  }

  @Override
  public void end(boolean interrupted) {
    this.buffer.stop();
    this.intake.stop();
    this.shooter.stop();
  }
}
