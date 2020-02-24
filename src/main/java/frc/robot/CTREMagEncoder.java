/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Do NOT add any static variables to this class, or any initialization at all.
 * Unless you know what you are doing, do not modify this file except to change
 * the parameter class to the startRobot call.
 */

    //motor.getSelectedSensorPosition();
    //motor.getSelectedSensorVelocity();
    //motor.setSelectedSensorPosition(sensorPos)
public class CTREMagEncoder  {

    private int zeroDistance;
    private DigitalInput limitSwitch;
    private int switchMode;
    private WPI_TalonSRX motorAssigned;
    public static final int NORMALLYOPEN = 0;
    public static final int NORMALLYCLOSED = 1;
    


    /**
     * @param motor The Talon motor the CTREMAgEncode will refernce
     */
    public CTREMagEncoder (WPI_TalonSRX motor){
        motorAssigned = motor;
        motorAssigned.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        zeroDistance = motor.getSelectedSensorPosition();
    }


    /**
     * @param motor The Talon motor the CTREMAgEncode will refernce
     * @param feedback The Feedback device, ie encoder, that the motor will refernece
     */
    public CTREMagEncoder (WPI_TalonSRX motor, FeedbackDevice feedback){
        motorAssigned = motor;
        motorAssigned.configSelectedFeedbackSensor(feedback);
        zeroDistance = motor.getSelectedSensorPosition();
    }


    /**
     * @param motor The Talon motor the CTREMAgEncode will refernce
     * @param limitSwitchPort The port for the limit switch
     * @param switchMode1 The mode the limit switch will operate in by default. 0 - default open. 1 - default closed
     */
    public CTREMagEncoder (WPI_TalonSRX motor, int limitSwitchPort, int switchMode1){
        motorAssigned = motor;
        motorAssigned.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        zeroDistance = motor.getSelectedSensorPosition();
        limitSwitch = new DigitalInput(limitSwitchPort);
        switchMode = switchMode1;

    }



    /**
     * @param motor The Talon motor the CTREMAgEncode will refernce
     * @param feedback The Feedback device, ie encoder, that the motor will refernece
     * @param limitSwitchPort The port for the limit switch
     * @param switchMode1 The mode the limit switch will operate in by default. 0 - default open. 1 - default closed
     */
    public CTREMagEncoder (WPI_TalonSRX motor, FeedbackDevice feedback, int limitSwitchPort, int switchMode1){
        motorAssigned = motor;
        motorAssigned.configSelectedFeedbackSensor(feedback);
        zeroDistance = motor.getSelectedSensorPosition();
        limitSwitch = new DigitalInput(limitSwitchPort);
        switchMode = switchMode1;

    }
    

    /**
     * @return Returns the current raw speed of the motor associated with the MagEncoder
     */
    public int getSpeed(){
        return motorAssigned.getSelectedSensorVelocity();
    }


    /**
     * @return Returns the current raw encoder position of the encooder associated with the Motor
     */
    public int getRawPosition(){
        return motorAssigned.getSelectedSensorPosition();
    }


    /**
     * @return Returns the current encoder position minus the zero distance of the encooder associated with the Motor
     */
    public int getPosition(){
        return motorAssigned.getSelectedSensorPosition() - zeroDistance;
    }


    /**
     * @return Sets the zeroDistance to the current position of the encoder
     */
    public void setCurrentPositionZero(){
        zeroDistance = motorAssigned.getSelectedSensorPosition();
    }


    /**
     * @return Returns if a ceratin limit switch is "activated" depending on the limitswitch port and mode specified in the constructor.
     */
    public boolean checkLimitSwitchPeriodic(){
        boolean returnVal = false;
        
        if (limitSwitch.get() && switchMode == 0){
            returnVal = true;
            this.setCurrentPositionZero();
        }
        else if (!limitSwitch.get() && switchMode == 1){
            returnVal = true;
            this.setCurrentPositionZero();
        }
        return returnVal;
    }

}
