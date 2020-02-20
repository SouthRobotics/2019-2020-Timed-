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
public class CTREMagEncoder  {

    private int zeroDistance;
    private DigitalInput limitSwitch;
    private int switchMode;
    private WPI_TalonSRX motorAssigned;
    public static final int NormallyOpenLimitSwitch = 0;
    public static final int NormallyClosedLimitSwitch = 1;
    



    public CTREMagEncoder (WPI_TalonSRX motor){
        motorAssigned = motor;
        motorAssigned.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        zeroDistance = motor.getSelectedSensorPosition();
    }

    public CTREMagEncoder (WPI_TalonSRX motor, FeedbackDevice feedback){
        motorAssigned = motor;
        motorAssigned.configSelectedFeedbackSensor(feedback);
        zeroDistance = motor.getSelectedSensorPosition();
        
        //motor.getSelectedSensorPosition();
        //motor.getSelectedSensorVelocity();
        //motor.setSelectedSensorPosition(sensorPos)
    }

    public CTREMagEncoder (WPI_TalonSRX motor, int limitSwitchPort, int switchMode1){
        motorAssigned = motor;
        motorAssigned.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        zeroDistance = motor.getSelectedSensorPosition();
        limitSwitch = new DigitalInput(limitSwitchPort);
        switchMode = switchMode1;

    }

    public CTREMagEncoder (WPI_TalonSRX motor, FeedbackDevice feedback, int limitSwitchPort, int switchMode1){
        motorAssigned = motor;
        motorAssigned.configSelectedFeedbackSensor(feedback);
        zeroDistance = motor.getSelectedSensorPosition();
        limitSwitch = new DigitalInput(limitSwitchPort);
        switchMode = switchMode1;

    }
    
    public int getSpeed(){
        return motorAssigned.getSelectedSensorVelocity();
    }

    public int getRawPosition(){
        return motorAssigned.getSelectedSensorPosition();
    }

    public int getPosition(){
        return motorAssigned.getSelectedSensorPosition() - zeroDistance;
    }

    public void setCurrentPositionZero(){
        zeroDistance = motorAssigned.getSelectedSensorPosition();
    }

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
