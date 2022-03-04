package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Intake;

public class CollectCargoTimer extends CommandBase {
  private final Intake intake;

  private Timer timer;

  private double secondsEnabled;

  public CollectCargoTimer(double secondsEnabled, Intake intake) {
    this.intake = intake;

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
    this.intake.set(ShooterConstants.speed);
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
