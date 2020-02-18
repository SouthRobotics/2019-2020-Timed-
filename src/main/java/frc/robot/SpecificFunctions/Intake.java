/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.SpecificFunctions;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Arduino.*;

/**
 * This is a robot specific class that is used for the intake mechanism of the robot
 * 
 * This class will automatically turn on the intake if the pixy camera sees a ball, and it will stay on for a specified duration
 */
public class Intake {

    private PixyCam pixy;
    private Timer timer;
    private WPI_TalonSRX[] intakeMotors, conveyerMotors;
    private double[] multipliers;
    private double multiplier;
    private int timeOut;
    private double width, height;

    public static final double DEFAULT_MIN_HEIGHT = .5;
    public static final double DEFAULT_MIN_WIDTH = .5;
    public static final int DEFAULT_BAUD_RATE = 9600;
    public static final int DEFAULT_TIMEOUT = 10;

    /**
     * 
     * @param intakePorts All of the ports of the Talon SRXs the motors on the intake are hooked up to
     * @param conveyerPorts All of the ports the Talon SRXs the motors for the conveyer system are hooked up to 
     * @param motorMultipliers Multipliers for the individual motor's speed. In the same order as the intakePorts array + the converyPorts array
     * @param multiplier Multiplier for all of the motors
     * @param baudRate Baud rate for the Pixy camera
     * @param timerMax The time the conveyer and intake system should be on once the pixy sees a ball
     * @param minWidth The minimum width of a tracked object for the intake system to turn on, from 0 to 1
     * @param minHeight The minimum height of a tracked object for the intake system to furn on, from 0 to 1
     */

    public Intake(int[] intakePorts, int[] conveyerPorts, double[] motorMultipliers, double allMultiplier, int baudRate, int timerMax, double minWidth, double minHeight)
    {
        pixy = new PixyCam(baudRate);

        height = minHeight;
        width = minWidth;

        multiplier = allMultiplier;

        intakeMotors = new WPI_TalonSRX[intakePorts.length];
        for(int i = 0; i < intakePorts.length; i++)
            intakeMotors[i] = new WPI_TalonSRX(intakePorts[i]);

        conveyerMotors = new WPI_TalonSRX[conveyerPorts.length];
        for(int i = 0; i < conveyerPorts.length; i++)
            conveyerMotors[i] = new WPI_TalonSRX(conveyerPorts[i]);

        for(int i = 0; i < motorMultipliers.length; i++)
            multipliers[i] = motorMultipliers[i] * multiplier;
    }

    /**
     * 
     * @param intakePorts All of the ports of the Talon SRXs the motors on the intake are hooked up to
     * @param conveyerPorts All of the ports the Talon SRXs the motors for the conveyer system are hooked up to
     * @param motorMultipliers Multipliers for the individual motor's speed. In the same order as the intakePorts array + the converyPorts array
     * @param multiplier Multiplier for all of the motors
     * @param minWidth The minimum width of a tracked object for the intake system to turn on, from 0 to 1
     * @param minHeight The minimum height of a tracked object for the intake system to furn on, from 0 to 1
     */

    public Intake(int[] intakePorts, int[] conveyerPorts, double[] motorMultipliers, double multiplier, double minWidth, double minHeight)
    {
        this(intakePorts, conveyerPorts, motorMultipliers, multiplier, Intake.DEFAULT_BAUD_RATE, Intake.DEFAULT_TIMEOUT, minWidth, minHeight);
    }

    /**
     * 
     * @param intakePorts All of the ports of the Talon SRXs the motors on the intake are hooked up to
     * @param conveyerPorts All of the ports the Talon SRXs the motors for the conveyer system are hooked up to
     * @param multiplier Multiplier for all of the motors
     * @param minWidth The minimum width of a tracked object for the intake system to turn on, from 0 to 1
     * @param minHeight The minimum height of a tracked object for the intake system to furn on, from 0 to 1
     */

    public Intake(int[] intakePorts, int[] conveyerPorts, double multiplier, double minWidth, double minHeight)
    {
        this(intakePorts, conveyerPorts, new double[intakePorts.length + conveyerPorts.length], multiplier, Intake.DEFAULT_BAUD_RATE, Intake.DEFAULT_TIMEOUT, minWidth, minHeight);

        for(int i = 0; i < multipliers.length; i++)
            multipliers[i] = 1;
    }

    /**
     * 
     * @param intakePorts All of the ports of the Talon SRXs the motors on the intake are hooked up to
     * @param conveyerPorts All of the ports the Talon SRXs the motors for the conveyer system are hooked up to
     * @param multiplier Multiplier for all of the motors
     * @param minWidth The minimum width of a tracked object for the intake system to turn on, from 0 to 1
     * @param minHeight The minimum height of a tracked object for the intake system to furn on, from 0 to 1
     */

    public Intake(int[] intakePorts, int[] conveyerPorts)
    {
        this(intakePorts, conveyerPorts, new double[intakePorts.length + conveyerPorts.length], 1, Intake.DEFAULT_BAUD_RATE, Intake.DEFAULT_TIMEOUT, Intake.DEFAULT_MIN_WIDTH, Intake.DEFAULT_MIN_HEIGHT);

        for(int i = 0; i < multipliers.length; i++)
            multipliers[i] = 1;
    }


    /**
     * This method should be used in a loop when the intake is deisred to be used
     * This will check if there is an object that meets the required size, and if there is then it will turn the motors on for a set ammount of time
     * 
     * @return Returns true if the intake is currently on, and false if the intake is currently off
     */

    public boolean intakePeriodic()
    {
        double[] blocks = pixy.getBlocks();
        if(blocks[2] >= width && blocks[3] >= height)
        {

            for(int i = 0; i < intakeMotors.length; i++)
                intakeMotors[i].set(multipliers[i] * multiplier);
            
            for(int i = 0; i < conveyerMotors.length; i++)
                conveyerMotors[i].set(multipliers[i + intakeMotors.length] * multiplier);
            timer.reset();
            timer.start();
            return true;
        }
        else if(timer.get() > timeOut)
        {
            timer.stop();
            timer.reset();

            for(int i = 0; i < intakeMotors.length; i++)
                intakeMotors[i].set(0);
            
            for(int i = 0; i < conveyerMotors.length; i++)
                conveyerMotors[i].set(0);
            return false;
        }
        else
            return true;
        
    }
}
