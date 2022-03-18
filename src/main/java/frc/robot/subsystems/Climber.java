package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {
  private final WPI_VictorSPX motor;

  public Climber() {
    this.motor = new WPI_VictorSPX(ClimberConstants.motorPort);

    this.motor.setInverted(ClimberConstants.isInverted);
  }

  public void set(double speed) {
    this.motor.set(ControlMode.PercentOutput, speed);
  }

  public void stop() {
    this.motor.set(ControlMode.PercentOutput, 0);
  }
}
