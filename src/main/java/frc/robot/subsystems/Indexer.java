package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase {
    private CANSparkMax indexMotor;

    public Indexer() {
        indexMotor = new CANSparkMax(8, MotorType.kBrushless);
    }

    @Override
    public void periodic() {

    }

    public void push(double speed) {
        indexMotor.set(speed);
    }
}
