// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistribution;
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

  //Global State of Shooter
  public static boolean isShooting = false;

  //Touch Sensors
  public static DigitalInput indexTouch = new DigitalInput(0); //Ball Indexer

  //PhotonCamera
  public static PhotonCamera camera = new PhotonCamera("Live!_Cam_Sync_HD_VF0070");

  // Smart Dashboard and Auto Chooser
  private static final String kDefaultAuto = "Default";
  private static final String kShootMoveBack = "ShootMoveBack";
  private static final String kAimShootRapidLogo = "AimShootRapidLogo";
  private static final String kFindBall = "FindBall";
  private static final String kAimTester = "AimTester";
  private static final String kLimelightAim = "LimelightAim";
  private static final String kBlue1 = "Blue1";
  private static final String kBlue2 = "Blue2";
  private static final String kBlue3 = "Blue3";
  private static final String kBLue4 = "Blue4";
  private static final String kRed1 = "Red1";
  private static final String kRed2 = "Red2";
  private static final String kRed3 = "Red3";
  private static final String kRed4 = "Red4";

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

    //Auto Game Programs
    m_chooser.addOption("Blue1", kBlue1);
    m_chooser.addOption("Blue2", kBlue2);
    m_chooser.addOption("Blue3", kBlue3);
    m_chooser.addOption("Blue4", kBLue4);

    m_chooser.addOption("Red1", kRed1);
    m_chooser.addOption("Red2", kRed2);
    m_chooser.addOption("Red3", kRed3);
    m_chooser.addOption("Red4", kRed4);

    String [] choices = {kFindBall, kBlue1, kBlue2, kBlue3, kBLue4, kRed1, kRed2, kRed3, kRed4}; 
    SmartDashboard.putStringArray("Auto List", choices);

    // Uncomment below to clear Sticky Faults
    //PowerDistribution pdp = new PowerDistribution();
    //pdp.clearStickyFaults();
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
    //System.out.println("Auto selected: " + m_autoSelected);

    switch (m_autoSelected) {
      case kShootMoveBack:
        // Call Game Auto Function Here
        //System.out.println("Running ShootMoveBack");
        m_autonomousCommand = m_robotContainer.getShootMoveBackCommand();
        break;
      case kAimShootRapidLogo:
        // Call Game RedB Function Here
        //System.out.println("Running AimShootRapidLogo");
        m_autonomousCommand = m_robotContainer.getAimShootRapidLogoCommand();
        //redB();
        break;
      case kFindBall:
        m_autonomousCommand = m_robotContainer.getPickUpBallCommand();
        //System.out.println("Running FindBall");
        break;
      case kAimTester:
        //System.out.println("Running AimTester");
        // m_autonomousCommand = m_robotContainer.getAimTesterCommand();
        m_autonomousCommand = m_robotContainer.getPickUpBallCommand();
        break;
      case kLimelightAim:
        m_autonomousCommand = m_robotContainer.getLimelightAimCommand();
        break;
      case kBlue1:
        //System.out.println("Running Blue 1 Autonomous");
        camera.setPipelineIndex(1);
        m_autonomousCommand = m_robotContainer.getBlue1Command();
        break;
      case kBlue2:
        //System.out.println("Running Blue 2 Autonomous");
        camera.setPipelineIndex(1);
        m_autonomousCommand = m_robotContainer.getBlue2Command();
        break;
      case kBlue3:
        //System.out.println("Running Blue 3 Autonomous");
        camera.setPipelineIndex(1);
        m_autonomousCommand = m_robotContainer.getBlue3Command();
        break;
      case kBLue4:
        //System.out.println("Running Blue 4 Autonomous");
        camera.setPipelineIndex(1);
        m_autonomousCommand = m_robotContainer.getBlue4Command();
        break;
      case kRed1:
        //System.out.println("Running Red 1 Autonomous");
        camera.setPipelineIndex(2);
        m_autonomousCommand = m_robotContainer.getRed1Command();
        break;
      case kRed2:
        //System.out.println("Running Red 2 Autonomous");
        camera.setPipelineIndex(2);
        m_autonomousCommand = m_robotContainer.getRed2Command();
        break;
      case kRed3:
        //System.out.println("Running Red 3 Autonomous");
        camera.setPipelineIndex(2);
        m_autonomousCommand = m_robotContainer.getRed3Command();
        break;
      case kRed4:
        //System.out.println("Running Red 4 Autonomous");
        camera.setPipelineIndex(2);
        m_autonomousCommand = m_robotContainer.getRed4Command();
        break;

      case kDefaultAuto:
      default:
        // Put default auto code here
        // Nothing Happens Here
        //System.out.println("Running Default Code");
        break;
    }

    //m_autonomousCommand = m_robotContainer.getEncoderTestCommand();
    
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
  public void teleopPeriodic() {
    //System.out.println(indexTouch.get());
  }

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
