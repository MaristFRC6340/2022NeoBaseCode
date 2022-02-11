// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class DriveTrain extends SubsystemBase {
  private CANSparkMax leftFront;
  private CANSparkMax leftRear;
  private CANSparkMax rightFront;
  private CANSparkMax rightRear;
  private SparkMaxPIDController m_pidController;
  private RelativeEncoder m_leftEncoder;
  private RelativeEncoder m_rightEncoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;


  /** Creates a new DriveTrain. */
  public DriveTrain() {
    leftFront = new CANSparkMax(0, MotorType.kBrushless);
    leftRear = new CANSparkMax(1,  MotorType.kBrushless);
    rightFront = new CANSparkMax(2,  MotorType.kBrushless);
    rightRear = new CANSparkMax(3,  MotorType.kBrushless);
    /**
     * The restoreFactoryDefaults method can be used to reset the configuration parameters
     * in the SPARK MAX to their factory default state. If no argument is passed, these
     * parameters will not persist between power cycles
     */
    leftFront.restoreFactoryDefaults();
    rightFront.restoreFactoryDefaults();

    /**
     * In order to use PID functionality for a controller, a SparkMaxPIDController object
     * is constructed by calling the getPIDController() method on an existing
     * CANSparkMax object
     */
    m_pidController = leftFront.getPIDController();

    // Encoder object created to display position values
    m_leftEncoder = leftFront.getEncoder();
    m_rightEncoder = rightFront.getEncoder();

    // PID coefficients
    kP = 0.1; 
    kI = 1e-4;
    kD = 1; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 1; 
    kMinOutput = -1;

    // set PID coefficients
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);

    // // display PID coefficients on SmartDashboard
    // SmartDashboard.putNumber("P Gain", kP);
    // SmartDashboard.putNumber("I Gain", kI);
    // SmartDashboard.putNumber("D Gain", kD);
    // SmartDashboard.putNumber("I Zone", kIz);
    // SmartDashboard.putNumber("Feed Forward", kFF);
    // SmartDashboard.putNumber("Max Output", kMaxOutput);
    // SmartDashboard.putNumber("Min Output", kMinOutput);
    // SmartDashboard.putNumber("Set Rotations", 0);
    leftRear.follow(leftFront, false);
    rightRear.follow(rightFront, false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive(double left, double right) {
    leftFront.set(left);
    leftRear.set(left);
    rightFront.set(right);
    rightRear.set(right);
  }

  public void driveDistance(double distance) {
    
  }
  
  //getters for encoder drive command
  public RelativeEncoder getLeftEnc() {
    return m_leftEncoder;
  }

  public RelativeEncoder getRightEnc() {
    return m_rightEncoder;
  }

  
}
