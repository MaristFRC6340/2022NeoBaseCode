// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final double intakeSpeed = 0.5;
    public static double speedMultiplier = .8;
    public static double maxPivotSpeed = 1;
    public static final double MAX_MOTOR_POWER = .35;
    public static final double MOTOR_POWER_SCALAR = -1/240.0;
    public static final double CENTER_TOLERANCE = 3;
    public static final double FORWARD_POWER = -.25; //currently unused
    public static final double VISION_OFFSET = 7; //currently unused, using parameters for different autonomi
    public static final double TURN_POWER = 0.35;
}
