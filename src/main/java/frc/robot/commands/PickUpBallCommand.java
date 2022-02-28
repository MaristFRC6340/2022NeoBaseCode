package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class PickUpBallCommand extends SequentialCommandGroup {
    public PickUpBallCommand(DriveTrain drivetrain, Indexer indexer, Intake intake) {
        addCommands(
          new VisionDriveCommand2(drivetrain, 0.001, 3),
          new DriveTimeCommand(drivetrain, -0.3, 3)
        );
    }    
}