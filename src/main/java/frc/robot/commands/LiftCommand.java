// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Lift;

public class LiftCommand extends CommandBase {
  private Lift lift;
  private double liftSpeed;
  private double pivotSpeed;

  /** Creates a new LiftCommand. */
  public LiftCommand(Lift lft, double lSpeed, double pSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.lift = lft;
    this.liftSpeed = lSpeed;
    this.pivotSpeed = pSpeed;

    addRequirements(lift);
  }

  public LiftCommand(Lift lft) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.lift = lft;

    this.liftSpeed = 0;
    this.pivotSpeed = 0;

    addRequirements(lift);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //pivotSpeed = 0;
    //liftSpeed = 0;

    //liftarms
    pivotSpeed = Robot.getJoyLogi2().getRawAxis(5);

    // if(Robot.getJoyLogi().getRawAxis(2) > 0) {
    //   pivotSpeed = 0.5;
    // }
    // else if(Robot.getJoyLogi().getRawAxis(3) < 0) {
    //   pivotSpeed = -0.5;
    // }
    // else {
    //   pivotSpeed =  0;
    // }

    liftSpeed = Robot.getJoyLogi2().getRawAxis(1);

    if (Math.abs(liftSpeed) < 0.1) {
      liftSpeed = 0;
    }

    lift.pivot(pivotSpeed * Constants.maxPivotSpeed);
    lift.raise(liftSpeed); 

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    lift.pivot(0);
    lift.raise(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
