/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.SpecificFunctions;

import edu.wpi.first.wpilibj.Servo;

/**
 * Add your docs here.
 */
public class OuttakeLift {

    private Servo servo;
    private final double MAX = 1;
    private final double MIN = .2;

    public OuttakeLift(int channel)
    {
        servo = new Servo(channel);
    }

    public OuttakeLift(Servo s)
    {
        servo = s;
    }

    public void set(boolean value)
    {
        if(value)
            servo.set(MAX);
        else  
            servo.set(MIN);
    }
}
