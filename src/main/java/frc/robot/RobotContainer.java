package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.core.util.TrajectoryBuilder;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.ShootCargo;
import frc.robot.commands.autonsTrajectory.Test;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.intake.CollectCargo;
import frc.robot.commands.intake.ReleaseCargo;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
  private Drivetrain drivetrain;
  private Intake intake;
  private final Shooter shooter;
  private Buffer buffer;

  
  private TrajectoryBuilder trajectoryBuilder;

  private XboxController driver;
  private XboxController operator;

  public RobotContainer() {
    this.drivetrain = new Drivetrain();
    this.intake = new Intake();
    this.shooter = new Shooter();
    this.buffer = new Buffer();


    this.driver = new XboxController(OIConstants.driverControllerPort);
    this.operator = new XboxController(OIConstants.operatorControllerPort);

    this.trajectoryBuilder = new TrajectoryBuilder(
      this.drivetrain,
      "testAuto1",
      "testAuto2"
    );

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    this.buttonBindingsDrivetain();
    this.buttonBindingsIntake();
    this.buttonBindingsBuffer();
    this.buttonBindingsShooter();
  }

  private void buttonBindingsDrivetain() {
    var RB = new JoystickButton(this.driver, Button.kRightBumper.value);

    this.drivetrain.setDefaultCommand(
      new ArcadeDrive(
        this.drivetrain, 
        () -> this.driver.getLeftY(), 
        () -> this.driver.getRightX()
      )
    );

    RB.whenPressed(() ->  this.drivetrain.setMaxOutput(0.25));
    RB.whenReleased(() -> this.drivetrain.setMaxOutput(1));
  }

  private void buttonBindingsIntake() {
    var LB = new JoystickButton(this.driver, Button.kLeftBumper.value);
    var LT = new JoystickButton(this.driver, Axis.kLeftTrigger.value);

    LB.whenPressed(new CollectCargo(this.intake));
    LT.whenPressed(new ReleaseCargo(this.intake));
  }

  private void buttonBindingsShooter() {
    var buttonA = new JoystickButton(this.driver, Button.kA.value);

    buttonA.whileHeld(new ShootCargo(this.shooter));
  }

  
  private void buttonBindingsBuffer() {
    var buttonB = new JoystickButton(this.driver, Button.kB.value);

    buttonB.whileHeld(new ShootCargo(this.shooter));
  }


  public Command getAutonomousCommand() {
    return new Test(this.trajectoryBuilder);
  }

  public void reset() {
    this.drivetrain.reset();
  }
}
