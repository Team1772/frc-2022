package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BufferConstants;

public class Buffer extends SubsystemBase {
  private final VictorSPX motor;

  public Buffer() {
    this.motor = new VictorSPX(BufferConstants.motorPort);

    this.motor.setInverted(true);
  }

  public void set(double speed) {
    this.motor.set(ControlMode.PercentOutput, speed);
  }

  public void stop() {
    this.motor.set(ControlMode.PercentOutput, 0);
  }
}
