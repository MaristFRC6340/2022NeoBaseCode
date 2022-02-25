// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class ShootTimeCommand extends CommandBase {
  /** Creates a new ShootTimeCommand. */
  private Shooter shooter;
  private Intake intake;
  private Indexer indexer;
  private double power;
  private long startTime = 0;
  private long endTime = 0;
  private double duration = 0;

  private final long DELAY = 2000;
  private final double PUSH_SPEED = .5;

  public ShootTimeCommand(Shooter s, Intake take, Indexer index, double pow, double time) {
    // Use addRequirements() here to declare subsystem dependencies.
    shooter = s;
    intake = take;
    indexer = index;
    power = pow;
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
    long currentTime = System.currentTimeMillis();
    shooter.setPower(power);

    if(currentTime - startTime > DELAY)
    {
      indexer.push(-PUSH_SPEED);
      intake.pickUp(PUSH_SPEED);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setPower(0);
    indexer.push(0);
    intake.pickUp(0);
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
