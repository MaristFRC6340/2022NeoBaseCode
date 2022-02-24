// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class EncoderTestCommand extends SequentialCommandGroup {
  //private DriveTrain drivetrain;

  /** Creates a new EncoderTestCommand. */
  public EncoderTestCommand(DriveTrain drivetrain) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    
    // From Mr. Michaud - this seems to work now
    // We will test again on Thursday Feb 24th
    addCommands(
       new EncoderDriveCommand(drivetrain, 12, 0.1),
       new EncoderDriveCommand(drivetrain, -12, 0.1),
       //new EncoderDriveCommand(drivetrain, -12, 0.1)
       new EncoderTurnCommand(drivetrain, 90, 0.1),
       //new EncoderDriveCommand(drivetrain, -12, 0.1),
       new EncoderTurnCommand(drivetrain, -90, 0.1)
    );
  }
}
