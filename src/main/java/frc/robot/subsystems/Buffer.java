package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Buffer extends SubsystemBase {
    private final VictorSPX motor;

    public Buffer() {
        this.motor = new VictorSPX(0);
    }

    public void setSpeed(double speed){
        this.motor.set(ControlMode.PercentOutput, speed);
    }
  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
