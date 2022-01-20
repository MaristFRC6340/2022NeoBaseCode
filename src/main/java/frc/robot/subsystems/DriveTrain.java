// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;;


public class DriveTrain extends SubsystemBase {

  // private Spark leftFront;
  // private Spark leftRear;
  // private Spark rightFront;
  // private Spark rightRear;
  private CANSparkMax leftFront;
  private CANSparkMax leftRear;
  private CANSparkMax rightFront;
  private CANSparkMax rightRear;


  /** Creates a new DriveTrain. */
  public DriveTrain() {
    leftFront = new CANSparkMax(0, MotorType.kBrushless);
    leftRear = new CANSparkMax(1,  MotorType.kBrushless);
    rightFront = new CANSparkMax(2,  MotorType.kBrushless);
    rightRear = new CANSparkMax(3,  MotorType.kBrushless);

    leftFront.setInverted(false);
    leftRear.setInverted(false);
    rightFront.setInverted(true);
    rightRear.setInverted(true);
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

  
}
