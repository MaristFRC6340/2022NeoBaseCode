// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TwoBallShootCommand extends SequentialCommandGroup {
  /** Creates a new TwoBallShootCommand. */
  public TwoBallShootCommand(DriveTrain driveTrain, Intake intake, Indexer indexer, Shooter shooter) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new EncoderDriveIntakeCommand(driveTrain, intake, 65, 0.2),
      new EncoderTurnCommand(driveTrain, 180, 0.2),
      new LimelightTimeAimCommand(driveTrain, 0.02, 1),
      new EncoderDriveCommand(driveTrain, 25, 0.2),
      new LimelightTimeAimCommand(driveTrain, 0.02, 1),
      new ShootTimeCommand(shooter, intake, indexer, 0.65, 2.5),
      new EncoderDriveCommand(driveTrain, -10, 0.2)
    );
  }
}
