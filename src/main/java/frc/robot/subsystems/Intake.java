package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Intake extends SubsystemBase {
  private VictorSPX motor;

  public Intake() {
    this.motor = new VictorSPX(IntakeConstants.motorPort);
    this.motor.setInverted(IntakeConstants.isInverted);
  }

  public void set(double speed) {
    this.motor.set(ControlMode.PercentOutput, speed);
  }

  public void stop() {
    this.motor.set(ControlMode.PercentOutput, 0);
  }
}
