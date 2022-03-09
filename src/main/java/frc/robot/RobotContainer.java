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
import frc.core.util.oi.OperatorRumble;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.autonsTrajectory.BlueBottomStartTopThreeCargos;
import frc.robot.commands.autonsTrajectory.BlueTopStartCenterTwoCargos;
import frc.robot.commands.autonsTrajectory.BlueTopStartTopTwoCargos;
import frc.robot.commands.buffer.RollbackToShoot;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.intake.SmartCollect;
import frc.robot.commands.shooter.Shoot;
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
  SendableChooser<Command> autonomousChooser;


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
      "exitTarmac1",
      "reverseAlignCargo1",
      "getCargoAndStopToShoot1",
      "exitTarmac2",
      "reverseAlignAndStopToShoot2",
      "reverseAlignCargo2",
      "getCargo2",
      "alignAndStopToShoot2",
      "alignCargoAndGet3",
      "reverseAlignAndStopToShoot3",
      "alignCargoAndGet4",
      "reverseAlignAndStopToShoot4"
    );

    this.autonomousChooser = new SendableChooser<>();

    var blueTopStartCenterTwoCargos = new BlueTopStartCenterTwoCargos(drivetrain, intake, buffer, shooter, trajectoryBuilder);
    var blueBottomStartTopThreeCargos = new BlueBottomStartTopThreeCargos(drivetrain, intake, buffer, shooter, trajectoryBuilder);

    this.autonomousChooser.addOption("blue top start center two cargos", blueTopStartCenterTwoCargos);
    this.autonomousChooser.addOption("blue bottom start top three cargos", blueBottomStartTopThreeCargos);

    SmartDashboard.putData(this.autonomousChooser);

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
        () -> -this.driver.getLeftY(), 
        () -> -this.driver.getRightX()
      )
    );
  }

  private void buttonBindingsIntake() {
    var leftBumper = new JoystickButton(this.operator, Button.kLeftBumper.value);

    leftBumper.whileHeld(new SmartCollect(this.intake, this.buffer, this.shooter));
  }

  private void buttonBindingsShooter() {
    var buttonBumperRight = new JoystickButton(this.operator, Button.kRightBumper.value);
    
    buttonBumperRight.whileHeld(new Shoot(this.intake, this.buffer, this.shooter));
  }

  private void buttonBindingsBuffer() {
    var buttonX = new JoystickButton(this.operator, Button.kX.value);

    var shooterFree = new Trigger(() -> this.buffer.isAllInfraredsOff());

    buttonX.and(shooterFree).whileActiveContinuous(new OperatorRumble(this.operator, true));
    buttonX.whileHeld(new RollbackToShoot(this.intake, this.buffer, this.shooter));
  }

  public Command getAutonomousCommand() {
    // return this.autonomousChooser.getSelected();
    return new BlueTopStartTopTwoCargos(drivetrain, intake, buffer, shooter, trajectoryBuilder);
  }

  public void reset() {
    this.drivetrain.reset();
  }

  public void setRumble(double value) {
    this.operator.setRumble(RumbleType.kLeftRumble, value);
  }
}
