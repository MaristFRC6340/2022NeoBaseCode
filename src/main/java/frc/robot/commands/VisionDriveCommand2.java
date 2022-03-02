// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class VisionDriveCommand2 extends CommandBase {
  private NetworkTable table;
  private NetworkTableEntry targetX;
  private NetworkTableEntry targetYaw;

  private DriveTrain drivetrain;

  private long duration;
  private long startTime;
  private double power;
  private double offset;

  private final double minPower = .03;

  /** Creates a new VisionDriveCommand2. */
  public VisionDriveCommand2(DriveTrain dTrain, double pwr, double dur, double offset) {
    // Use addRequirements() here to declare subsystem dependencies.
    table = NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("Live!_Cam_Sync_HD_VF0770");
    targetX = table.getEntry("targetPixelsX");
    targetYaw = table.getEntry("targetYaw");
    this.offset = offset;

    drivetrain = dTrain;
    power = pwr;
    duration = (long)(dur*1000);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double error = targetYaw.getDouble(0);

    error += offset;

    double left, right;
    left = error * -power;
    right = error *  power;

      //Set bounds on left power
      if(left > 0.3) {
          left = 0.3;
      } else if(left < -0.3) {
          left = -0.3;
      }

      //Set bounds on right power
      if(right > 0.3) {
          right = 0.3;
      } else if(right < -0.3) {
          right = -0.3;
      }

    drivetrain.drive(left, right);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return System.currentTimeMillis() > startTime + duration;
  }
}
