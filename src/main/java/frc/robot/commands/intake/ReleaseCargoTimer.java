package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.util.NumberUtil;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Intake;

public class ReleaseCargoTimer extends CommandBase {
  private final Intake intake;

  private Timer timer;
  private double secondsEnabled;

  public ReleaseCargoTimer(double secondsEnabled, Intake intake) {
    this.intake = intake;

    this.timer = new Timer();

    addRequirements(this.intake);
  }

  @Override
  public void initialize() {
      this.timer.reset();
      this.timer.start();
  }

  @Override
  public void execute() {
      this.intake.set(NumberUtil.invert(0.5));
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