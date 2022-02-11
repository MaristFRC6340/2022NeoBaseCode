// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private Command m_teleopCommand;

  private RobotContainer m_robotContainer;
  
  private static Joystick leftJoystick = new Joystick(0);
  private static Joystick rightJoystick = new Joystick(1);
  private static Joystick joyLogi = new Joystick(2);
  private static Joystick joyLogi2 = new Joystick(3);

  // Smart Dashboard and Auto Chooser
  private static final String kDefaultAuto = "Default";
  private static final String kShootMoveBack = "ShootMoveBack";
  private static final String kAimShootRapidLogo = "AimShootRapidLogo";
  private static final String kFindBall = "FindBall";
  private static final String kAimTester = "AimTester";
  private static final String kLimelightAim = "LimelightAim";
  
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    // Initialize Chooser
    
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("ShootMoveBack", kShootMoveBack);
    m_chooser.addOption("AimShootRapidLogo", kAimShootRapidLogo);
    m_chooser.addOption("FindBall", kFindBall);
    m_chooser.addOption("AimTester", kAimTester);
    m_chooser.addOption("Limelight Aim", kLimelightAim);
    String [] choices = {kDefaultAuto, kShootMoveBack, kAimShootRapidLogo, kFindBall, kAimTester, kLimelightAim};
    SmartDashboard.putStringArray("Auto List", choices);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    //m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

    switch (m_autoSelected) {
      case kShootMoveBack:
        // Call Game Auto Function Here
        System.out.println("Running ShootMoveBack");
        m_autonomousCommand = m_robotContainer.getShootMoveBackCommand();
        break;
      case kAimShootRapidLogo:
        // Call Game RedB Function Here
        System.out.println("Running AimShootRapidLogo");
        m_autonomousCommand = m_robotContainer.getAimShootRapidLogoCommand();
        //redB();
        break;
      case kFindBall:
        System.out.println("Running FindBall");
        break;
      case kAimTester:
        System.out.println("Running AimTester");
        // m_autonomousCommand = m_robotContainer.getAimTesterCommand();
        m_autonomousCommand = m_robotContainer.getPickUpBallCommand();
        break;
      case kLimelightAim:
        m_autonomousCommand = m_robotContainer.getLimelightAimCommand();
      case kDefaultAuto:
      default:
        // Put default auto code here
        // Nothing Happens Here
        System.out.println("Running Default Code");
        break;
    }

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    m_teleopCommand = m_robotContainer.getTeleopCommand();
    if (m_teleopCommand != null) {
      m_robotContainer.getIndexerCommand().schedule();
      m_robotContainer.getIntakeCommand().schedule();
      m_teleopCommand.schedule();
      m_robotContainer.getShooterCommand().schedule();
      m_robotContainer.getLiftCommand().schedule();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  public static Joystick getLeftJoystick()
  {
    return leftJoystick;
  }

  public static Joystick getRightJoystick()
  {
    return rightJoystick;
  }

  public static Joystick getJoyLogi()
  {
    return joyLogi;
  }

  public static Joystick getJoyLogi2() {
    return joyLogi2;
  }
}
