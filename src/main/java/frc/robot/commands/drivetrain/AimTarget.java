// package frc.robot.commands.drivetrain;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.core.components.Limelight;
// import frc.core.components.Limelight.LedMode;
// import frc.robot.Constants.LimelightConstants;
// import frc.robot.subsystems.Drivetrain; 

// public class AimTarget extends CommandBase {

//   private static final int TARGET = 1;
//   private static final double NO_SPEED = 0.0;
//   private static final double RESET_STEERING = 0.0;

//   private Drivetrain drivetrain;
//   private double X_axis;
//   private double steeringAdjust;
//   private double headingError;
//   private double leftSpeed;
//   private double rightSpeed;

//   public AimTarget(Drivetrain drivetrain) {
//     this.drivetrain = drivetrain;

//     this.X_axis = Limelight.getX();
//     this.steeringAdjust = RESET_STEERING;

//     this.headingError = negate(this.X_axis);

//     this.leftSpeed = NO_SPEED;
//     this.rightSpeed = NO_SPEED;

//     addRequirements(this.drivetrain);
//   }

//   @Override
//   public void initialize() {
//     Limelight.setLed(LedMode.ON);
//     Limelight.setPipeline(LimelightConstants.pipeline);
//   }

//   @Override
//   public void execute() {
//     this.resetSteeringAdjust();

//     this.setAim();

//     this.steerToTarget();
//   }

//   private void resetSteeringAdjust() {
//     this.steeringAdjust = RESET_STEERING;
//   }

//   private void setAim() {
//     if (this.isSteerLeft()) {
//       this.steeringAdjust = LimelightConstants.AimTarget.kP * 
//                             headingError - 
//                             LimelightConstants.AimTarget.minCommand;

//     } else if (this.isSteerRight()) { 
//       this.steeringAdjust = LimelightConstants.AimTarget.kP * 
//                             headingError + 
//                             LimelightConstants.AimTarget.minCommand;
//     }
//   }

//   private boolean isSteerLeft() {
//     return this.X_axis > TARGET;
//   }

//   private boolean isSteerRight() {
//     return this.X_axis < TARGET;
//   }

//   private void steerToTarget() {
//     this.resetSteeringSpeed();

//     this.leftSpeed += this.steeringAdjust;
//     this.rightSpeed -= this.steeringAdjust;

//     this.drivetrain.tankDrive(this.leftSpeed, this.rightSpeed);
//   }

//   private void resetSteeringSpeed() {
//     this.leftSpeed = NO_SPEED;
//     this.rightSpeed = NO_SPEED;
//   }

//   private double negate(double value) {
//     return -value;
//   }

//   @Override
//   public void end(boolean interrupted) {
//     Limelight.setLed(LedMode.OFF);
//   }
// }

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.core.components.Limelight;
import frc.core.components.Limelight.LedMode;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.Drivetrain; 

public class AimTarget extends CommandBase {
  private Drivetrain drivetrain;

  public AimTarget(Drivetrain drivetrain) {
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
    double x = Limelight.getX(),
      headingError = -(x),
      adjust = 0;

    if (x > 1) {
      adjust = LimelightConstants.AimTarget.kP * 
                headingError - 
                LimelightConstants.AimTarget.minCommand;
    } else if (x < 1) { 
      adjust = LimelightConstants.AimTarget.kP * 
                headingError + 
                LimelightConstants.AimTarget.minCommand;
    }
    double rightSpeed = 0,
      leftSpeed = 0;
    rightSpeed -= adjust;
    leftSpeed += adjust;

    this.drivetrain.tankDrive(leftSpeed, rightSpeed);

    System.out.println("adjust: " + adjust);
    System.out.println("left speed: " + leftSpeed);
    System.out.println("right speed: " + rightSpeed);
    
  }

  @Override
  public void end(boolean interrupted) {
    Limelight.setLed(LedMode.OFF);
  }
}