/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.SpecificFunctions;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.OI;

public class Outtake {

    private WPI_TalonSRX motor;
    private double multiplier;
    private OI oi;
    private int button, index;

    /**
     * 
     * @param port Port for the Talon SRX the motor is connected to
     * @param _multiplier Multiplier for the speed value of the motor
     * @param inputs The OI for the robot
     * @param buttonNumber The number of the button that should be pressed to turn the outtake on
     * @param cnumber The index of the controller (Ex: for tankdrive there are two joysticks, so cnumber will be which joystick to get the button from)
     */

    public Outtake(int port, double _multiplier, OI inputs, int buttonNumber, int cnumber)
    {
        motor = new WPI_TalonSRX(port);
        multiplier = _multiplier;
        oi = inputs;
        button = buttonNumber;
        index = cnumber;
    }

    /**
     * 
     * @param port Port for the Talon SRX the motor is connected to
     * @param inputs The OI for the robot
     * @param buttonNumber The number of the button that should be pressed to turn the outtake on
     * @param cnumber The index of the controller (Ex: for tankdrive there are two joysticks, so cnumber will be which joystick to get the button from)
     */

    public Outtake(int port, OI inputs, int buttonNumber, int cnumber)
    {
        motor = new WPI_TalonSRX(port);
        multiplier = 1;
        oi = inputs;
        button = buttonNumber;
        index = cnumber;
    }

    /**
     * 
     * @return True if outtake is on, false if it is off
     */

    public boolean outTakePeriodic()
    {
        if(oi.getButton(button)[index])
        {
            motor.set(multiplier);
            return true;
        }
        else
            return false;
    }
}
