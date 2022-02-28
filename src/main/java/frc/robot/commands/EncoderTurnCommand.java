package frc.robot.commands;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class EncoderTurnCommand extends  CommandBase{
    //fields
    private double angleDegrees;
    private double power;
    private double target;
    private double error;
    private double kP = 0.005;

    private DriveTrain drivetrain;
    private RelativeEncoder leftEncoder;
    private RelativeEncoder rightEncoder;

    public EncoderTurnCommand(DriveTrain dtrain, double turnDegs, double pwr)
    {
        drivetrain = dtrain;
        angleDegrees = turnDegs;
        power = pwr;

        leftEncoder = drivetrain.getLeftEnc();
        rightEncoder = drivetrain.getRightEnc();

        addRequirements(drivetrain);
    }

    public void initialize()
    {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
        error = 0;

        target = angleDegrees * .1; //constant to be tuned
    }

    public void execute()
    {
        error = leftEncoder.getPosition() + rightEncoder.getPosition();
        // double turnAdjust = error * kP;
        double turnAdjust = 0;
        if(target > 0)
            drivetrain.drive(-(power - turnAdjust), -(-power - turnAdjust));
        else
            drivetrain.drive(power - turnAdjust, -power - turnAdjust);

    }

    public void end(boolean interrupted)
    {
        drivetrain.drive(0, 0);
    }

    public boolean isFinished() {

        double currentAngle = leftEncoder.getPosition();
    
        if(target > 0 && currentAngle > target) {
          return true;
        }
        if(target < 0 && currentAngle < target) {
          return true;
        }

        //System.out.println(currentAngle);
        return false;
      }
}
