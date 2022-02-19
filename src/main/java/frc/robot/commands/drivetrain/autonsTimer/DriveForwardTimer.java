package frc.robot.commands.drivetrain.autonsTimer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Drivetrain.Arcade;
import edu.wpi.first.wpilibj.Timer;

public class DriveForwardTimer extends CommandBase {
  
  private final Drivetrain drivetrain;
  private final Timer timer = new Timer();
  private double speed;
  private double secs;


  public DriveForwardTimer( double secs, double speed, Drivetrain drivetrain) {
    this.secs = secs;
    this.speed = speed;
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
    this.drivetrain.arcadeDrive(speed, 0.0);
  }
  
  @Override
  public boolean isFinished() {
    return this.timer.get() > secs;
  }

  @Override
  public void end(boolean interrupted) {
    this.drivetrain.arcadeDrive(Arcade.stop(), Arcade.noRotation());
    timer.reset();
  }
}
