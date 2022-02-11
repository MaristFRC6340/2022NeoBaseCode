// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Indexer;

public class IndexerCommand extends CommandBase {
  private final Indexer indexer;
  private final double power;
  /** Creates a new IndexerCommand. */
  public IndexerCommand(Indexer indexer, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.indexer = indexer;
    this.power = power;
    
    addRequirements(indexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Robot.getJoyLogi().getRawAxis(3) > 0.5) {
      indexer.push(-0.7);
    } else {
      indexer.push(power*Robot.getJoyLogi().getRawAxis(5));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    indexer.push(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
