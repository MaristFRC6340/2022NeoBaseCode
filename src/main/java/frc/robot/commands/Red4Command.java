// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Red4Command extends SequentialCommandGroup {
  /** Creates a new Red4Command. */
  public Red4Command(DriveTrain driveTrain, Intake intake, Indexer indexer, Shooter shooter) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ShootTimeCommand(shooter, intake, indexer, 0.4, 2.5),
      new EncoderTurnCommand(driveTrain, -150, Constants.TURN_POWER),
      new VisionDriveCommand2(driveTrain, 0.005, 1.5, 7),
      new EncoderDriveIntakeCommand(driveTrain, intake, 90, 0.2),
      new EncoderTurnCommand(driveTrain, 210, Constants.TURN_POWER),
      new LimelightTimeAimCommand(driveTrain, 0.02, 2),
      new ShootTimeCommand(shooter, intake, indexer, 0.5, 2.5)
    );
  }
}
