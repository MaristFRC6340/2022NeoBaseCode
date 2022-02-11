// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AimTimeCommand extends CommandBase {
  /** Creates a new AimTimeCommand. */

  private DriveTrain drivetrain;
  private long startTime;
  private double duration;
  private double power;

  private NetworkTable table;
  private NetworkTableEntry xPos;

  public AimTimeCommand(DriveTrain dtrain, double pwr, double time) {
    // Use addRequirements() here to declare subsystem dependencies.

    drivetrain = dtrain;
    duration = time;
    power = pwr;

    table = NetworkTableInstance.getDefault().getTable("logotable");
    xPos = table.getEntry("xPos");

    addRequirements(drivetrain);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = System.currentTimeMillis();
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double error = xPos.getDouble(0.0)-110;
    double kP = 0.03;
    error *= kP;
    double turnPower = power*error;
    if(turnPower > 0.4) {
      turnPower = 0.4;
    }
    if(turnPower < -0.4) {
      turnPower = -0.4;
    }
    System.out.println(error*power);
    drivetrain.drive(-turnPower, turnPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    long endTime = startTime + (long)(duration*1000);

    return System.currentTimeMillis() >= endTime;
  }
}
