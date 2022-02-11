// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.AimShootRapidLogoCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IndexerCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.LiftCommand;
import frc.robot.commands.LimelightAimCommand;
import frc.robot.commands.PickUpBallCommand;
import frc.robot.commands.ShootMoveBackCommand;
import frc.robot.commands.ShootTimeCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.TankDrive;
import frc.robot.commands.VisionDriveCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Lift;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private DriveTrain drivetrain = new DriveTrain();

  private final TankDrive m_tankDrive = new TankDrive(drivetrain);

  private final Shooter m_shooter = new Shooter();

  private final ShooterCommand m_shootercommand = new ShooterCommand(m_shooter);

  private final Lift m_lift = new Lift();

  private final LiftCommand m_liftcommand = new LiftCommand(m_lift);

  private final LimelightAimCommand m_lLimelightAimCommand = new LimelightAimCommand(drivetrain, 0.05);


  // private final ShooterCommand m_shooterLowPower = new ShooterCommand(m_shooter, Shooter.Power.LOW);
  // private final ShooterCommand m_shooterMediumPower = new ShooterCommand(m_shooter, Shooter.Power.MEDIUM);
  // private final ShooterCommand m_shooterHighPower = new ShooterCommand(m_shooter, Shooter.Power.HIGH);

  private final Intake m_Intake = new Intake();

  private final IntakeCommand m_IntakeCommand = new IntakeCommand(m_Intake, 0.75);

  private final Indexer m_Indexer = new Indexer();

  private final IndexerCommand m_IndexerCommand = new IndexerCommand(m_Indexer, 0.75);

  private final ShootTimeCommand m_ShootTimeCommand = new ShootTimeCommand(m_shooter, m_Intake, m_Indexer, .75, 5);

  private final ShootMoveBackCommand m_ShootMoveBackCommand = new ShootMoveBackCommand(drivetrain, m_shooter, m_Indexer, m_Intake);

  private final VisionDriveCommand m_AimTester = new VisionDriveCommand(drivetrain, .04, 10);

  private final AimShootRapidLogoCommand m_aAimShootRapidLogoCommand = new AimShootRapidLogoCommand(drivetrain, m_shooter, m_Indexer, m_Intake);

  private final PickUpBallCommand m_pPickUpBallCommand = new PickUpBallCommand(drivetrain, m_Indexer, m_Intake);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // new JoystickButton(Robot.getRightJoystick(), 3).whileHeld(m_shooterLowPower);
    // new JoystickButton(Robot.getRightJoystick(), 4).whileHeld(m_shooterMediumPower);
    // new JoystickButton(Robot.getRightJoystick(), 5).whileHeld(m_shooterHighPower);
    // new JoystickButton(Robot.getLeftJoystick(), 4).whileHeld(m_IntakeCommand);
    // new JoystickButton(Robot.getLeftJoystick(), 6).whileHeld(m_IndexerCommand);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

  public Command getTeleopCommand() {
    return m_tankDrive;
  }

  public Command getIntakeCommand() {
    return m_IntakeCommand;
  }

  public Command getIndexerCommand() {
    return m_IndexerCommand;
  }

  public Command getShooterCommand() {
    return m_shootercommand;
  }

  public Command getLiftCommand() {
     return m_liftcommand;
  }

  public Command getShootTimeCommand()
  {
    return m_ShootTimeCommand;
  }

  public Command getShootMoveBackCommand()
  {
    return m_ShootMoveBackCommand;
  }

  public Command getAimTesterCommand()
  {
    return m_AimTester;
  }

  public Command getAimShootRapidLogoCommand() {
    return m_aAimShootRapidLogoCommand;
  }

  public Command getLimelightAimCommand() {
    return m_lLimelightAimCommand;
  }

  public Command getPickUpBallCommand() {
    return m_pPickUpBallCommand;
  }

}
