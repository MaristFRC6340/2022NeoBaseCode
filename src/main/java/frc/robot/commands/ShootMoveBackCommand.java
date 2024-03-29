// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup; 
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.DriveTrain;

public class ShootMoveBackCommand extends SequentialCommandGroup {
  /** Creates a new ShootMoveBackCommand. */
  public ShootMoveBackCommand(DriveTrain drivetrain, Shooter shooter, Indexer indexer, Intake intake) {
    addCommands(
      new DriveTimeCommand(drivetrain, 0.3, 2),
      new ShootTimeCommand(shooter, intake, indexer, 0.72, 3)
    );
  }
}
