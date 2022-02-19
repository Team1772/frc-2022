package frc.robot.autonsPID;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.DrivetrainConstants.PID;
import frc.robot.subsystems.Drivetrain;

public class DriveForward extends PIDCommand {

  private static final double POSITION_TOLERANCE = 0.01;

  private final Drivetrain drivetrain;
  
  public DriveForward(double distance, Drivetrain drivetrain) {
    super(
        new PIDController(
            PID.kPDriveVelocity, 
            PID.kIDriveVelocity, 
            PID.kDDriveVelocity),
             
        drivetrain::getAverageDistance, 
        distance, 
        d -> drivetrain.tankDrive(d, d));

    this.drivetrain = drivetrain;
    addRequirements(this.drivetrain);

    getController().setTolerance(POSITION_TOLERANCE);
  }

  @Override
  public void initialize() {
    this.drivetrain.reset();
    super.initialize();
  }

  @Override
  public boolean isFinished() {
    return super.getController().atSetpoint();
  }
}