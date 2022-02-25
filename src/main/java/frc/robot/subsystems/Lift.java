package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Lift extends SubsystemBase {
    private CANSparkMax leftArm;
    private CANSparkMax rightArm;
    private CANSparkMax lift;
    
    public Lift() {
        leftArm = new CANSparkMax(9, MotorType.kBrushed);   // Must be Brushed Motors: Mr. Michaud 25 Feb 22
        rightArm = new CANSparkMax(10, MotorType.kBrushed); // Must be Brushed Motors: 
        lift = new CANSparkMax(11, MotorType.kBrushless);
    }

    @Override
    public void periodic() {

    }

    
    public void pivot(double speed) {
        leftArm.set(speed);
        rightArm.set(-speed);
    }

    public void raise(double speed) {
        lift.set(speed);
    }
}
