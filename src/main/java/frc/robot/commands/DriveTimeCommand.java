// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveTimeCommand extends CommandBase {

  private DriveTrain driveTrain;
  private double leftPower;
  private double rightPower;
  private double duration;
  private long startTime;
  private long endTime;

  /** Creates a new DriveTimeCommand. */
  public DriveTimeCommand(DriveTrain dt, double pow, double time) {
    // Use addRequirements() here to declare subsystem dependencies.
    driveTrain = dt;
    leftPower = rightPower = pow;
    duration = time;
  }

  public DriveTimeCommand(DriveTrain dt, double lPow, double rPow, double time) {
    // Use addRequirements() here to declare subsystem dependencies.
    driveTrain = dt;
    leftPower =  lPow;
    rightPower = rPow;
    duration = time;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = System.currentTimeMillis();
    endTime = startTime + (long)(duration*1000);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveTrain.drive(leftPower, rightPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.drive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    long currentTime = System.currentTimeMillis();
    if(currentTime < endTime)
    {
      return false;
    }
    else
    {
      return true;
    }
  }
}
