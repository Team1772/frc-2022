package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class UseIntakeToShootTimer extends CommandBase {
  private final Intake intake;
  private final Shooter shooter;

  private Timer timer;

  private double secondsEnabled;

  public UseIntakeToShootTimer(Intake intake, Shooter shooter, double secondsEnabled) {
    this.intake = intake;
    this.shooter = shooter;

    this.timer = new Timer();
    this.secondsEnabled = secondsEnabled;

    addRequirements(this.intake);
  }


  @Override
  public void initialize() {
    this.timer.reset();
    this.timer.start();
  }

  @Override
  public void execute() {
    if (this.shooter.atSettedVelocity()) {
      this.intake.set(ShooterConstants.speed);
    }
  }

  @Override
  public boolean isFinished() {
    return this.timer.get() > this.secondsEnabled;
  }
  @Override
  public void end(boolean isInterrupted) {
    this.intake.stop();
    this.timer.stop();
  }
}
