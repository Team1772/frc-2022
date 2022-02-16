package frc.robot.commands.drivetrain.autonsTimer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Drivetrain.Arcade;
import edu.wpi.first.wpilibj.Timer;

public class DriveReverseTimer extends CommandBase {
  private final Timer timer = new Timer();
  private final Drivetrain drivetrain;
  private double secs;
  private double speed;

  public DriveReverseTimer(double secs, double speed, Drivetrain drivetrain) {
    this.secs = secs;
    this.speed = -(speed);
    this.drivetrain = drivetrain;

    addRequirements(this.drivetrain);
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    this.drivetrain.arcadeDrive(-(speed), 0.0);
  }

  @Override
  public void end(boolean interupted){
    this.drivetrain.arcadeDrive(Arcade.stop(), Arcade.noRotation());
    timer.reset();
  }

  @Override
  public boolean isFinished() {
    return this.timer.get() > secs;
  }
}
