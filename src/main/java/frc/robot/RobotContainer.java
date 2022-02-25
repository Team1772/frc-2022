package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.core.util.TrajectoryBuilder;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.autonsEncoder.AutonomousEncoders;
import frc.robot.commands.buffer.ForwardFeed;
import frc.robot.commands.buffer.RollbackToShoot;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.intake.SmartFeed;

import frc.robot.commands.shooter.ShootCargo;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
  private final Drivetrain drivetrain;
  private final Intake intake;
  private final Shooter shooter;
  private final Buffer buffer;

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
    this.drivetrain.setDefaultCommand(
      new ArcadeDrive(
        this.drivetrain, 
        () -> this.driver.getLeftY(), 
        () -> this.driver.getRightX()
      )
    );
  }

  private void buttonBindingsIntake() {
    var leftBumper = new JoystickButton(this.operator, Button.kLeftBumper.value);

    leftBumper.whileHeld(new SmartFeed(intake, buffer));
  }

  private void buttonBindingsShooter() {
    var buttonA = new JoystickButton(this.operator, Button.kA.value);
                                                       
    buttonA.whileHeld(new ShootCargo(this.shooter));
  }

  private void buttonBindingsBuffer() {
    var rightBumper = new JoystickButton(this.operator, Button.kRightBumper.value);
    var buttonX = new JoystickButton(this.operator, Button.kX.value);

    rightBumper.whileHeld(new ForwardFeed(this.buffer));
    buttonX.whileHeld(new RollbackToShoot(this.intake, this.buffer, this.shooter));
  }

  public Command getAutonomousCommand() {
    return new AutonomousEncoders(this.drivetrain);
  }

  public void reset() {
    this.drivetrain.reset();
  }
}
