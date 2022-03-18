package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.core.util.TrajectoryBuilder;
import frc.core.util.oi.DriveteamRumble;
import frc.core.util.oi.OperatorRumble;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.autonsTrajectory.BlueBottomStartBottomTwoCargos;
import frc.robot.commands.autonsTrajectory.BlueBottomStartTopThreeCargos;
import frc.robot.commands.autonsTrajectory.BlueTopStartCenterTwoCargos;
import frc.robot.commands.autonsTrajectory.BlueTopStartCenterTwoCargosUpdated;
import frc.robot.commands.autonsTrajectory.BlueTopStartTopTwoCargos;
import frc.robot.commands.buffer.Rollback;
import frc.robot.commands.buffer.RollbackToShoot;
import frc.robot.commands.climber.ChangeClimberSize;
import frc.robot.commands.drivetrain.AimAndRangeTarget;
import frc.robot.commands.drivetrain.AimTarget;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.RangeTarget;
import frc.robot.commands.intake.ReleaseCargo;
import frc.robot.commands.intake.SmartCollect;
import frc.robot.commands.shooter.Shoot;
import frc.robot.subsystems.Buffer;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
  private final Drivetrain drivetrain;
  private final Intake intake;
  private final Shooter shooter;
  private final Buffer buffer;
  private final Climber climber;

  private TrajectoryBuilder trajectoryBuilder;

  private XboxController driver;
  private XboxController operator;

  public RobotContainer() {
    this.drivetrain = new Drivetrain();
    this.intake = new Intake();
    this.shooter = new Shooter();
    this.buffer = new Buffer();
    this.climber = new Climber();

    this.driver = new XboxController(OIConstants.driverControllerPort);
    this.operator = new XboxController(OIConstants.operatorControllerPort);

    this.trajectoryBuilder = new TrajectoryBuilder(
      this.drivetrain,
      // "exitTarmac1",
      // "reverseAlignCargo1",
      // "getCargoAndStopToShoot1",
      // "exitTarmac2",
      // "reverseAlignAndStopToShoot2",
      // "reverseAlignCargo2",
      // "getCargo2",
      // "alignAndStopToShoot2",
      // "alignCargoAndGet3",
      // "reverseAlignAndStopToShoot3",
      // "alignCargoAndGet4",
      // "reverseAlignAndStopToShoot4",
      "exitTarmac5",
      "reverseAlignCargo5",
      "getCargoAndStopToShoot5"
    );

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    this.buttonBindingsDrivetain();
    this.buttonBindingsIntake();
    this.buttonBindingsBuffer();
    this.buttonBindingsShooter();
    this.buttonBindingsClimber();
  }

  private void buttonBindingsDrivetain() {
    this.drivetrain.setDefaultCommand(
      new ArcadeDrive(
        this.drivetrain, 
        () -> -this.driver.getLeftY(), 
        () -> -this.driver.getRightX()
      )
    );

    var leftBumper = new JoystickButton(this.driver, Button.kLeftBumper.value);
    var rightBumper = new JoystickButton(this.driver, Button.kRightBumper.value);

    leftBumper.whileHeld(new AimTarget(this.drivetrain));
    rightBumper.whileHeld(new RangeTarget(this.drivetrain));
  }

  private void buttonBindingsIntake() {
    var leftBumper = new JoystickButton(this.operator, Button.kLeftBumper.value);

    leftBumper.whileHeld(new SmartCollect(this.intake, this.buffer, this.shooter));
  }

  private void buttonBindingsShooter() {
    var buttonBumperRight = new JoystickButton(this.operator, Button.kRightBumper.value);
    var buttonY = new JoystickButton(this.operator, Button.kY.value);
    
    buttonBumperRight.whileHeld(new Shoot(25, this.intake, this.buffer, this.shooter));
    buttonY.whileHeld(new Shoot(10, intake, buffer, shooter));
  }

  private void buttonBindingsBuffer() {
    var leftStick = new JoystickButton(this.operator, Button.kLeftStick.value);

    leftStick.whenHeld(
      new Rollback(
        () -> this.operator.getRightY(), 
        intake, 
        buffer, 
        shooter
      )
    );

    var buttonX = new JoystickButton(this.operator, Button.kX.value);

    var shooterFree = new Trigger(() -> this.buffer.isAllInfraredsOff());

    buttonX.and(shooterFree).whileActiveContinuous(new OperatorRumble(this.operator, true));
    buttonX.whileHeld(new RollbackToShoot(this.intake, this.buffer, this.shooter));
  }

  public void buttonBindingsClimber() {

    var rightStick = new JoystickButton(this.operator, Button.kRightStick.value);

    rightStick.whenHeld(
      new ChangeClimberSize(
        () -> this.operator.getLeftY(), 
        this.climber
      )
    );
  }

  public Command getAutonomousCommand() {
    return new BlueTopStartCenterTwoCargosUpdated(drivetrain, intake, buffer, shooter, trajectoryBuilder);
  }

  public void reset() {
    this.drivetrain.reset();
  }

  public void setRumble(double value) {
    this.operator.setRumble(RumbleType.kLeftRumble, value);
  }
}
