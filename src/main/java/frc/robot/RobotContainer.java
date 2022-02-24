package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.core.util.TrajectoryBuilder;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.autonsEncoder.AutonomousEncoders;
import frc.robot.commands.autonsTrajectory.Test;
import frc.robot.commands.buffer.ForwardFeed;
import frc.robot.commands.buffer.ReleaseFeed;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.intake.CollectCargo;
import frc.robot.commands.intake.MatchColors;
import frc.robot.commands.intake.ReadRGBValues;
import frc.robot.commands.intake.ReleaseCargo;
import frc.robot.commands.intake.SmartIntake;
import frc.robot.commands.shooter.PullCargo;
import frc.robot.commands.shooter.ShootCargo;
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
    var rightBumper = new JoystickButton(this.driver, Button.kRightBumper.value);

    this.drivetrain.setDefaultCommand(
      new ArcadeDrive(
        this.drivetrain, 
        () -> this.driver.getLeftY(), 
        () -> this.driver.getRightX()
      )
    );

    rightBumper.whenPressed(() ->  this.drivetrain.setMaxOutput(0.25));
    rightBumper.whenReleased(() -> this.drivetrain.setMaxOutput(1));
  }

  private void buttonBindingsIntake() {
    var leftBumper = new JoystickButton(this.operator, Button.kLeftBumper.value);
    var buttonX = new JoystickButton(this.operator, Button.kX.value);

    leftBumper.whileHeld(new CollectCargo(this.intake));
    buttonX.whileHeld(new ReleaseCargo(this.intake));
  }

  private void buttonBindingsShooter() {
    var buttonA = new JoystickButton(this.operator, Button.kA.value);
    var buttonX = new JoystickButton(this.operator, Button.kX.value);
                                                       
    buttonA.whileHeld(new ShootCargo(this.shooter));
    buttonX.whileHeld(new PullCargo(this.shooter));
  }

  
  private void buttonBindingsBuffer() {
    var rightBumper = new JoystickButton(this.operator, Button.kRightBumper.value);
    var buttonX = new JoystickButton(this.operator, Button.kX.value);

    rightBumper.whileHeld(new ForwardFeed(this.buffer));
    buttonX.whileHeld(new ReleaseFeed(this.buffer));
  }


  public Command getAutonomousCommand() {
    return new AutonomousEncoders(this.drivetrain);
  }

  public void reset() {
    this.drivetrain.reset();
  }
}
