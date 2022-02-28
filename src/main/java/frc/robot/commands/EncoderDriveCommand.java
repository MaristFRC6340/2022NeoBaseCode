// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class EncoderDriveCommand extends CommandBase {

  //fields
  private double distanceFeet;
  private double power;
  private double target;
  private double error;
  private double kP = 0.005;

  private DriveTrain drivetrain;
  private RelativeEncoder leftEncoder;
  private RelativeEncoder rightEncoder;
 
  /** Creates a new EncoderDriveCommand. */
  public EncoderDriveCommand(DriveTrain dtrain, double tarDist, double pwr) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = dtrain;
    distanceFeet = tarDist;
    power = pwr;

    leftEncoder = drivetrain.getLeftEnc();
    rightEncoder = drivetrain.getRightEnc();

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    //reset Encoders + error
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
    error = 0;

    target = distanceFeet * 5.0 * 12.0 / 111.0;

    //System.out.println("here");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    error = leftEncoder.getPosition() - rightEncoder.getPosition();
    //double turnAdjust = error *kP;
    double turnAdjust = 0;
    if(target > 0)
      drivetrain.drive(-(power-turnAdjust), -(power+turnAdjust));
    else 
      drivetrain.drive(power-turnAdjust, power+turnAdjust);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(0, 0);
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    double currentDistance = leftEncoder.getPosition();

    if(target > 0 && currentDistance > target) {
      return true;
    }
    if(target < 0 && currentDistance < target) {
      return true;
    }
    //System.out.println(currentDistance);
    return false;
  }
}