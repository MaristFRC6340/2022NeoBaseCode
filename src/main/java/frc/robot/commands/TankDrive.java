// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.Constants;
import frc.robot.Robot;

public class TankDrive extends CommandBase {
  private final DriveTrain train;

  private final double minMotorPower = 0.1;

  /** Creates a new TankDrive. */
  public TankDrive(DriveTrain subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    train = subsystem;
    addRequirements(train);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double leftPower = Robot.getLeftJoystick().getY();
    double rightPower = Robot.getRightJoystick().getY();

    if(Math.abs(leftPower) < minMotorPower) {
      leftPower = 0;
    }
    if(Math.abs(rightPower) < minMotorPower) {
      rightPower = 0;
    }

    train.drive(Constants.speedMultiplier * leftPower, Constants.speedMultiplier * rightPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    train.drive(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public boolean runsWhenDisabled() {
    return false;
}
}
