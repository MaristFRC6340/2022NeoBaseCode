/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import javax.net.ssl.ExtendedSSLSession;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class VisionDriveCommand extends CommandBase {
  /**
   * Creates a new VisionDriveCommand.
   */
  private long startTime = 0;
  private long endTime = 0;
  private double startPower = 0;
  private double power = 0;
  //private double kP = 0 ;
  private double error = 0;
  private long duration = 0;
  private NetworkTable table;
  private NetworkTableEntry targetX;
  //private NetworkTableEntry tshort;
  //private NetworkTableEntry tlong;
  private NetworkTableEntry hasTarget;
  private NetworkTableEntry targetArea;
  private NetworkTableEntry targetY;
  private double[] seen = new double[50];
  //private double[] sizeHistory = new double[50];
  //private boolean moving = false;

  private double forwardPower = -0.3;

  private DriveTrain drivetrain;
  public VisionDriveCommand(DriveTrain dtrain, double pwr, long durat) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = dtrain;
    startPower = pwr;
    duration = durat;

    table = NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("Live!_Cam_Sync_HD_VF0770");
    targetX = table.getEntry("targetPixelsX");
    targetY = table.getEntry("targetPixelsY");
    // tshort = table.getEntry("tshort");
    // tlong = table.getEntry("tlong");
    hasTarget = table.getEntry("hasTarget");
    targetArea = table.getEntry("targetArea");

    
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = System.currentTimeMillis();
    endTime = startTime + duration * 1000;
    power = startPower;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    error = targetX.getDouble(0.0)-160;
    
    
  //System.out.println("targetX is : " + targetX + " and the error is: " + error);
    

    for(int i = seen.length-1; i > 0; i--) {
      seen[i] = seen[i-1];
    }

    seen[0] = hasTarget.getDouble(0.0);

    if(hasTarget.getBoolean(false))
    {
      seen[0] = 1;
    }
    else {
      seen[0] = 0;
    }

    int sum = 0;
    for(int i = 0; i < seen.length; i++) {
      if(seen[i] != 0) {
        sum++;
      }
    }

    if(error > 0 && error < 2*Constants.CENTER_TOLERANCE || error == -160.0) { //add sum < some number out of 50 for detection threshold
      drivetrain.drive(0.0, 0.0);
    } else {
      double calcPow = Constants.MOTOR_POWER_SCALAR*error;
      if(Math.abs(calcPow) < Constants.MAX_MOTOR_POWER)
      {
        System.out.println("Less than max, error = " + error + " and the calculated power is " + calcPow);
        drivetrain.drive(calcPow, -calcPow);
      }
      else
      {
        double signOfCalcPow = calcPow / Math.abs(calcPow);
        System.out.println("Greater than max, error = " + error + " and the calculated power is " + calcPow + " and the sign of calcPow is " + signOfCalcPow);
        drivetrain.drive(Constants.MAX_MOTOR_POWER * signOfCalcPow, -Constants.MAX_MOTOR_POWER * signOfCalcPow);
      }
    }

    //System.out.println(sum + " " + sizeSumGo + " " + sizeSumStop + " " + error);
    //System.out.println(power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(0, 0);
    

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    long currentTime = System.currentTimeMillis();
    if(currentTime < endTime)
      return false;
    else return true;
  }

  public boolean runsWhenDisabled() {
    return false;
  }
}
