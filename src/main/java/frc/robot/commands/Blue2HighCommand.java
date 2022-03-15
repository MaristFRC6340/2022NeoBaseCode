// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
/**
 * Blue 2 that can shoot high goal
 * Back up, shoot, back up
 * Noah Madelil 3/15/22
 */
public class Blue2HighCommand extends SequentialCommandGroup {


  /** Creates a new Blue2HighCommand. */
  public Blue2HighCommand(DriveTrain driveTrain, Intake intake, Indexer indexer, Shooter shooter) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new EncoderDriveCommand(driveTrain, -20, 0.2),
      new ShootTimeCommand(shooter, intake, indexer, 0.35, 2.5),
      new EncoderDriveCommand(driveTrain, -40, 0.2)
    );
  }
}
