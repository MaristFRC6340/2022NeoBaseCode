// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.Power;

public class ShooterCommand extends CommandBase {
  private final Shooter shooter;
  private final Power power;
  private double currentPower = 0;
  private int previousPov = -1; // previous value of the d-pad
  
  /** Creates a new ShooterCommand. */
  public ShooterCommand(Shooter shooter, Power power) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = shooter;
    this.power = power;

    addRequirements(shooter);
  }

  public ShooterCommand(Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = shooter;

    this.power = null;

    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    // Get Joystick input
    //Value changes made by Liam O'Toole on 2/26/2022
    if (Robot.getJoyLogi().getRawButton(1)) { // Button A
      currentPower = 0.3; //Low goal close shot
      shooter.setMotorPower(currentPower); // Sets Baseline Power
      Robot.isShooting = true;
    }
    if (Robot.getJoyLogi().getRawButton(2)) { // Button B - 14.5ft
      currentPower = 0.6; //high goal close shot
      shooter.setMotorPower(currentPower); // Sets Baseline Power
      Robot.isShooting = true;
    }
    if (Robot.getJoyLogi().getRawButton(3)) { // Button X
      currentPower = 0.82;
      // Power for high shots at de
      shooter.setMotorPower(currentPower); // Sets Baseline Power
      Robot.isShooting = true;
    }
    if(Robot.getJoyLogi().getRawButton(4)) { //Button Y - stop
      currentPower = 0;
      shooter.setMotorPower(currentPower); // Sets Baseline Power
      Robot.isShooting = false;
    }
    //if down is pressed on d-pad and nothing was pressed the previous cycle
    if(Robot.getJoyLogi().getPOV() == 180 && previousPov == -1)
    {
      currentPower -= 0.01; 
      shooter.setMotorPower(currentPower); // Sets Baseline Power
    }
    //if up is pressed on d-pad and nothing was pressed on the previous cycle
    if(Robot.getJoyLogi().getPOV() == 0 && previousPov == -1) {
      currentPower += 0.01; 
      shooter.setMotorPower(currentPower); // Sets Baseline Power
    }
    if(currentPower < 0) {
      currentPower = 0;
    }

    previousPov = Robot.getJoyLogi().getPOV();

    shooter.setPower(currentPower);
    //shooter.setRPM(currentPower*5676); // RPM Based

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setPower(0);
    // shooter.setPowerPID(currentPower);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
