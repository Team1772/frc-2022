package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HangerConstants;


public class Hanger extends SubsystemBase {
  private VictorSPX motorOne, motorTwo;

  public Hanger() {
    motorOne = new VictorSPX(HangerConstants.motorsPorts[0]);
    motorTwo = new VictorSPX(HangerConstants.motorsPorts[1]);
  }

  public void set(double speedOne, double speedTwo) {
    this.motorOne.set(ControlMode.PercentOutput, speedOne);
    this.motorTwo.set(ControlMode.PercentOutput, speedTwo);
  }

  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
