package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootTimer extends CommandBase {

  private final Shooter shooter;
  
  private Timer timer;
  
  private double secondsEnabled;
  private double shootVelocityMetersPerSecond;

  public ShootTimer(Shooter shooter, double secondsEnabled) {
    this.shooter = shooter;
    this.secondsEnabled = secondsEnabled;
    this.shootVelocityMetersPerSecond = 26;

    this.timer = new Timer();
    
    addRequirements(this.shooter);
  }

  public ShootTimer(double shootVelocityMetersPerSecond, Shooter shooter, double secondsEnabled) {
    this.shooter = shooter;

    this.secondsEnabled = secondsEnabled;
    this.shootVelocityMetersPerSecond = shootVelocityMetersPerSecond;

    this.timer = new Timer();
    
    addRequirements(this.shooter);
  }

  @Override
  public void initialize() {
    this.timer.reset();
    this.timer.start();
  }

  @Override
  public void execute() {
    if(this.shooter.isSafetyShoot()) {
      this.shooter.setVelocityMetersPerSecond(this.shootVelocityMetersPerSecond);
    }
  }

  @Override
  public boolean isFinished() {
    return this.timer.get() > this.secondsEnabled;
  }

  @Override
  public void end(boolean interrupted) {
    this.shooter.stop();
    this.timer.stop();
  }
}
