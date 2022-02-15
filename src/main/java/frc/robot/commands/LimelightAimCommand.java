// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

//currently runs forever
public class LimelightAimCommand extends CommandBase {
  /** Creates a new LimelightAimCommand. */
  private DriveTrain driveTrain;
  private double power;

  private NetworkTable limTable; // Limelight interface
  private NetworkTableEntry tx; // x value of limelight target
  private NetworkTableEntry ledMode; // controls the on/off state of the limelight led

  public LimelightAimCommand(DriveTrain dtrain, double pwr) {
    // Use addRequirements() here to declare subsystem dependencies.
    driveTrain = dtrain;
    power = pwr;

    limTable = NetworkTableInstance.getDefault().getTable("limelight");
    tx = limTable.getEntry("tx");
    ledMode = limTable.getEntry("ledMode");

    addRequirements(driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ledMode.setDouble(3); // turn on limelight lights
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double error = tx.getDouble(0);

    driveTrain.drive(-error*power, error*power);
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
    return false;
  }
}
