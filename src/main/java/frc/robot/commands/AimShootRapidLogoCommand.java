// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.DriveTrain;

public class AimShootRapidLogoCommand extends SequentialCommandGroup {
  /** Creates a new AimShootRapidLogoCommand. */
  public AimShootRapidLogoCommand(DriveTrain drivetrain, Shooter shooter, Indexer index, Intake take) {
    // Use addRequirements() here to declare subsystem dependencies.
    addCommands(
      new DriveTimeCommand(drivetrain, 0.5, 1),
      new AimTimeCommand(drivetrain, 0.2, 3),
      new ShootTimeCommand(shooter, take, index, 0.65, 3)
    );
  }
}
