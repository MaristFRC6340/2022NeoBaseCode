package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Red2Command extends SequentialCommandGroup {
    
    public Red2Command(DriveTrain driveTrain, Intake intake, Indexer indexer, Shooter shooter) {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(
          new ShootTimeCommand(shooter, intake, indexer, 2.5, 0.4),
          new EncoderDriveIntakeCommand(driveTrain, intake, -60, 0.2)
        );
      }
}
