// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.Constants;
import frc.robot.Robot;

public class TankDrive extends CommandBase {
  private final DriveTrain train;

  private final double minMotorPower = 0.1;

  private NetworkTable limTable; // Limelight table data
  private NetworkTableEntry tx; // x value of target from limelight
  private NetworkTableEntry ledMode; // Controls limelight led mode
  private final double power = 0.02; // Power constant for limelight aim

  /** Creates a new TankDrive. */
  public TankDrive(DriveTrain subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.

    limTable = NetworkTableInstance.getDefault().getTable("limelight");
    tx = limTable.getEntry("tx");
    ledMode = limTable.getEntry("ledMode");
    
    train = subsystem;
    addRequirements(train);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ledMode.setDouble(1); // turn limelight off
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double leftPower = Robot.getLeftJoystick().getY();
    double rightPower = Robot.getRightJoystick().getY();

    if(Robot.getJoyLogi().getRawAxis(2) > 0.5) { // If left trigger pressed on shooting controller
      ledMode.setDouble(3); // turn limelight on
      double error = tx.getDouble(0); // get error from limelight network table

      // Testing, increase error by 2 degrees
      error += 5;

      // Calculate left and right power
      leftPower = error * -power;
      rightPower = error *  power;

      //Set bounds on left power
      if(leftPower > 0.3) {
          leftPower = 0.3;
      } else if(leftPower < -0.3) {
          leftPower = -0.3;
      }

      //Set bounds on right power
      if(rightPower > 0.3) {
          rightPower = 0.3;
      } else if(rightPower < -0.3) {
          rightPower = -0.3;
      }
    } else { // Not in Auto Aiming Mode - uses parameters from driver as inputs
      ledMode.setDouble(1); // turn limelight off
    }

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
