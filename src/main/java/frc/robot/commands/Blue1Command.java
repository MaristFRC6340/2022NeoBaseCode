package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Blue1Command extends SequentialCommandGroup{
    
    public Blue1Command(DriveTrain driveTrain, Intake intake, Indexer indexer, Shooter shooter) {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());

        //adjust Constants.TURN_POWER in constants
        addCommands(
          new ShootTimeCommand(shooter, intake, indexer, 0.35, 2.5),
          new EncoderTurnCommand(driveTrain, 140, Constants.TURN_POWER),
          //new VisionDriveCommand2(driveTrain, 0.005, 1.5),
          new EncoderDriveIntakeCommand(driveTrain, intake, 80, 0.2),
          new EncoderTurnCommand(driveTrain, 210, Constants.TURN_POWER),
          new LimelightTimeAimCommand(driveTrain, 0.02, 2),
          new ShootTimeCommand(shooter, intake, indexer, 0.5, 2.5)
        );
      }
}
