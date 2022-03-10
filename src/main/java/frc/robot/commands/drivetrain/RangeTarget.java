
package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.components.Limelight;
import frc.core.components.Limelight.LedMode;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.Drivetrain; 

public class RangeTarget extends CommandBase {
  private Drivetrain drivetrain;

  public RangeTarget(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;

    addRequirements(this.drivetrain);
  }

  @Override
  public void initialize() {
    Limelight.setLed(LedMode.ON);
    Limelight.setPipeline(LimelightConstants.pipeline);
  }

  @Override
  public void execute() {
    double y = Limelight.getY();
    double distanceError = y;

    double rightSpeed = 0;
    double leftSpeed = 0;

    double drivingAdjust = LimelightConstants.RangeTarget.kPDistance * distanceError;
    rightSpeed += drivingAdjust;
    leftSpeed += drivingAdjust;

    this.drivetrain.tankDrive(leftSpeed, rightSpeed);


    System.out.println("adjust: " + drivingAdjust);
    System.out.println("distance error: " + distanceError);
    System.out.println("left speed: " + leftSpeed);
    System.out.println("right speed: " + rightSpeed);
    
  }

  @Override
  public void end(boolean interrupted) {
    Limelight.setLed(LedMode.OFF);
  }
}