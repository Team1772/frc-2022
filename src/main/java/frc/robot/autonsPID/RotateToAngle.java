package frc.robot.autonsPID;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.DrivetrainConstants.PID;
import frc.robot.subsystems.Drivetrain;

public class RotateToAngle extends PIDCommand {

  private static final int MAXIMUM_INPUT = 180;
  private static final int MINIMUM_INPUT = -180;

    public RotateToAngle(double targetAngleDegrees, Drivetrain drivetrain) {
      super(
          new PIDController(
            PID.kPDriveVelocity, 
            PID.kIDriveVelocity, 
            PID.kDDriveVelocity),

          () -> drivetrain.getHeading(), targetAngleDegrees,

          output -> drivetrain.arcadeDrive(0, output), drivetrain);
  
      super.getController().enableContinuousInput(MINIMUM_INPUT, MAXIMUM_INPUT);
      super.getController().setTolerance(PID.kTurnToleranceDeg, PID.kTurnRateToleranceDegPerS);
    }
  
    @Override
    public boolean isFinished() {
      return getController().atSetpoint();
    }
  }