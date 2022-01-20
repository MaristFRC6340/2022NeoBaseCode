// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain m_driveTrain = new DriveTrain();
  private final TankDrive m_tankDriveCommand = new TankDrive(m_driveTrain);

  private final Shooter m_shooter = new Shooter();

  private final ShooterCommand m_shooterLowPower = new ShooterCommand(m_shooter, Shooter.Power.LOW);
  private final ShooterCommand m_shooterMediumPower = new ShooterCommand(m_shooter, Shooter.Power.MEDIUM);
  private final ShooterCommand m_shooterHighPower = new ShooterCommand(m_shooter, Shooter.Power.HIGH);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    CommandScheduler.getInstance().registerSubsystem(m_driveTrain);

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
    new JoystickButton(Robot.getLeftJoystick(), 3).whileHeld(m_shooterLowPower);
    new JoystickButton(Robot.getLeftJoystick(), 4).whileHeld(m_shooterMediumPower);
    new JoystickButton(Robot.getLeftJoystick(), 5).whileHeld(m_shooterHighPower);

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }

  public Command getTeleopCommand() {
    return m_tankDriveCommand;
  }
}
