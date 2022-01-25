package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HangerConstants;


public class Hangar extends SubsystemBase {
  private VictorSPX motorOne, motorTwo;

  public Hangar() {
    motorOne = new VictorSPX(HangerConstants.motorsPorts[0]);
    motorTwo = new VictorSPX(HangerConstants.motorsPorts[1]);
  }

  public void set(double speed) {
    this.motorOne.set(ControlMode.PercentOutput, speed);
    this.motorTwo.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
