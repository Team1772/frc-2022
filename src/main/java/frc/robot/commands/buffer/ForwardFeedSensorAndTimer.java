package frc.robot.commands.buffer;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Shooter;

public class ForwardFeedSensorAndTimer extends CommandBase{
    private final Buffer buffer;
    private final Shooter shooter;

    private Timer timer;

    private double secondsEnabled;

    public ForwardFeedSensorAndTimer(Buffer buffer, Shooter shooter, double secondsEnabled) {
      this.buffer = buffer;
      this.shooter = shooter;
      this.secondsEnabled = secondsEnabled;

      this.timer = new Timer();
  
      addRequirements(this.buffer);
    }

    @Override
    public void initialize() {
      this.timer.reset();
      this.timer.start();
    }
  
    @Override
    public void execute() {
      if(this.shooter.atSettedVelocity()) {
        this.buffer.set(ShooterConstants.speed);
      }
    }

    @Override
    public boolean isFinished() {
      return this.timer.get() > this.secondsEnabled;
    }
  
    @Override
    public void end(boolean isInterrupted) {
      this.buffer.stop();
      this.timer.stop();
    }
}

