package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    public enum Power {
        OFF,
        LOW,
        MEDIUM,
        HIGH
    }

    private CANSparkMax m_shootMotor1;
    private CANSparkMax m_shootMotor2;
    private SparkMaxPIDController m_pidController;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

    public Shooter() {
        m_shootMotor1 = new CANSparkMax(4, MotorType.kBrushless);
        m_shootMotor2 = new CANSparkMax(5, MotorType.kBrushless);

        m_shootMotor1.restoreFactoryDefaults();
        m_shootMotor2.restoreFactoryDefaults();

        m_pidController = m_shootMotor1.getPIDController();

        kP = 6e-5;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0.000015;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 5700;

        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);

        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);
    }

    public void updateParameters() {
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);

        if((p != kP)) { m_pidController.setP(p); kP = p; }
        if((i != kI)) { m_pidController.setI(i); kI = i; }
        if((d != kD)) { m_pidController.setD(d); kD = d; }
        if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
        if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
        if((max != kMaxOutput) || (min != kMinOutput)) { 
          m_pidController.setOutputRange(min, max); 
          kMinOutput = min; kMaxOutput = max; 
        }
    }

    public void setPower(Power level) {
        switch(level) {
            case HIGH:
                m_pidController.setReference(.9*maxRPM, CANSparkMax.ControlType.kVelocity);
                break;
            case MEDIUM:
                m_pidController.setReference(.75*maxRPM, CANSparkMax.ControlType.kVelocity);
                break;
            case LOW:
                m_pidController.setReference(.5*maxRPM, CANSparkMax.ControlType.kVelocity);
                break;
            default:
                m_pidController.setReference(0, CANSparkMax.ControlType.kVelocity);
                break;
        }
        m_shootMotor2.follow(m_shootMotor1, true);
    }

}
