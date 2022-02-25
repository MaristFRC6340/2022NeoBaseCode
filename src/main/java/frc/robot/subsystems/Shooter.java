package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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
    private RelativeEncoder m_shootEncoder;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, motorPower;

    NetworkTableEntry velocityEntry;
    NetworkTableEntry targetEntry;
    NetworkTableEntry powerEntry;
    NetworkTableEntry errorEntry;
    NetworkTableEntry adjustEntry;
    NetworkTableEntry motorPowerEntry;

    public Shooter() {
        m_shootMotor1 = new CANSparkMax(5, MotorType.kBrushless);
        m_shootMotor2 = new CANSparkMax(6, MotorType.kBrushless);

        //m_shootMotor1.restoreFactoryDefaults();
        //m_shootMotor2.restoreFactoryDefaults();

        m_pidController = m_shootMotor1.getPIDController();
        m_shootEncoder = m_shootMotor1.getEncoder();

        kP = .0000004;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0.000015;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 5700;

        // m_pidController.setP(kP);
        // m_pidController.setI(kI);
        // m_pidController.setD(kD);
        // m_pidController.setIZone(kIz);
        // m_pidController.setFF(kFF);
        // m_pidController.setOutputRange(kMinOutput, kMaxOutput);

        // SmartDashboard.putNumber("P Gain", kP);
        // SmartDashboard.putNumber("I Gain", kI);
        // SmartDashboard.putNumber("D Gain", kD);
        // SmartDashboard.putNumber("I Zone", kIz);
        // SmartDashboard.putNumber("Feed Forward", kFF);
        // SmartDashboard.putNumber("Max Output", kMaxOutput);
        // SmartDashboard.putNumber("Min Output", kMinOutput);

        // Connect to Network Table
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        // Create Table
        NetworkTable table = inst.getTable("Shooter PID Table");

        velocityEntry = table.getEntry("Velocity");
        targetEntry = table.getEntry("Target Velocity");
        powerEntry = table.getEntry("Power");
        errorEntry = table.getEntry("Error");
        adjustEntry = table.getEntry("Adjust");
        motorPowerEntry = table.getEntry("Motor Power");

        m_shootMotor2.follow(m_shootMotor1, true);
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

    public void setPowerPID(double level) {
        // For Testing, we are commenting this
        if(level > .9) {
            level = .9;
        }
        if(level < .05) {
            m_pidController.setReference(0, CANSparkMax.ControlType.kVoltage);
        }
        else {
            m_pidController.setReference(level * maxRPM, CANSparkMax.ControlType.kVelocity);
        }
        
    }

    

    public void setPower(double power) {
        // limiter
        if (power > 0.9) {
            power = 0.9;
        }

        m_shootMotor1.set(power);
        m_shootMotor2.set(-power);
        //System.out.println("Current velocity of shooter: " + m_shootEncoder.getVelocity());

        // Netowork Table
        velocityEntry.setDouble(m_shootEncoder.getVelocity());
        powerEntry.setDouble(power);

        //m_pidController.setReference(power*maxRPM, CANSparkMax.ControlType.kVelocity);
        //m_shootMotor2.follow(m_shootMotor1, true);
    }


    public void setRPM(double rpm) {

        if (rpm == 0) {
            m_shootMotor1.set(0);
            m_shootMotor2.set(0);
        }
        else {
            // double power = rpm/maxRPM; // Depricated
    
            double error = rpm - m_shootEncoder.getVelocity();
    
            motorPower += kP * error; // Adjusted Global Variable
                                      // Motor Power remembered beteen frames
    
            //power += adjust; // Depricated
            
            if (motorPower > 0.9) {
                motorPower = 0.9;
            }

            if (motorPower < 0) {
                motorPower = 0;
            }
    
            m_shootMotor1.set(motorPower);
            m_shootMotor2.set(-motorPower);
            //System.out.println("Current velocity of shooter: " + m_shootEncoder.getVelocity());
    
            // Netowork Table - Print Values
            velocityEntry.setDouble(m_shootEncoder.getVelocity());
            motorPowerEntry.setDouble(motorPower);
            errorEntry.setDouble(error);
            targetEntry.setDouble(rpm);
            adjustEntry.setDouble(kP * error);
        }
        

    }

    /**
     * 
     * @param power range between 0 and 0.9
     * Sets the Baseline power for PID Algorithm
     */
    public void setMotorPower(double power) {
        motorPower = power;
    }
}
