// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

/**
 * Based on command from proto code
 * And tuning from DE - Noah Madelil
 */
public class LimelightTimeAimCommand extends CommandBase {
  /** Creates a new LimelightAimTimeCommand. */
  private DriveTrain driveTrain;
  private double power;

  private NetworkTable limTable; // Limelight interface
  private NetworkTableEntry tx; // x value of limelight target
  private NetworkTableEntry ledMode; // controls the on/off state of the limelight led

  private long duration;
  private long startTime;

  public LimelightTimeAimCommand(DriveTrain dtrain, double pwr, double dur) {
    // Use addRequirements() here to declare subsystem dependencies.
    driveTrain = dtrain;
    power = pwr;

    limTable = NetworkTableInstance.getDefault().getTable("limelight");
    tx = limTable.getEntry("tx");
    ledMode = limTable.getEntry("ledMode");

    duration = (long) (dur*1000);

    addRequirements(driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ledMode.setDouble(3); // turn on limelight lights
    startTime = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ledMode.setDouble(3); // turn on limelight lights
    double error = tx.getDouble(0);

    //Based on changes made at DE - may need to be turned
    error += -5;

    double left, right;
    left = error * -power;
    right = error *  power;

      //Set bounds on left power
      if(left > 0.2) {
          left = 0.2;
      } else if(left < -0.2) {
          left = -0.2;
      }

      //Set bounds on right power
      if(right > 0.2) {
          right = 0.2;
      } else if(right < -0.2) {
          right = -0.2;
      }

    driveTrain.drive(left, right);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.drive(0, 0);
    ledMode.setDouble(1); // turn off limelight lights
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return System.currentTimeMillis() > startTime + duration;
  }
}
