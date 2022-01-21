package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private final TalonSRX motor;

  public Shooter() {
    this.motor = new TalonSRX(Constants.ShooterConstants.motorPort);
  }

  public void setSpeed(double speed){
      this.motor.set(ControlMode.PercentOutput, speed);
  }

  public void stop(){
      this.motor.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
   
  }
}