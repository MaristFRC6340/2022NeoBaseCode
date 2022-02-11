package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private CANSparkMax intakeMotor;
    public Intake() {
        intakeMotor = new CANSparkMax(7, MotorType.kBrushless);
    }

    @Override
    public void periodic() {

    }

    public void pickUp(double speed) {
        intakeMotor.set(speed);
    }

}
