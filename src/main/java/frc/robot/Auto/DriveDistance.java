/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Auto;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.CTREMagEncoder;
import frc.robot.DriveTrain;

/**
 * Add your docs here.
 */
public class DriveDistance extends AutoProcess {
    private CTREMagEncoder leftEncoder, rightEncoder;
    private DriveTrain driveTrain;
    private double leftSpeed, rightSpeed, leftDistance, rightDistance;

    public DriveDistance(DriveTrain driveT, CTREMagEncoder leftE, CTREMagEncoder rightE, double distance, double speed){
        driveTrain = driveT;
        leftEncoder = leftE;
        rightEncoder = rightE;
        leftDistance = distance;
        rightDistance = distance;
        leftSpeed = speed;
        rightSpeed = speed;
    }

    public DriveDistance(DriveTrain driveT, CTREMagEncoder leftE, CTREMagEncoder rightE, double leftD, double rightD, double leftS, double rightS){
        driveTrain = driveT;
        leftEncoder = leftE;
        rightEncoder = rightE;
        leftDistance = leftD;
        rightDistance = rightD;
        leftSpeed = leftS;
        rightSpeed = rightS;
    }

    public DriveDistance(DriveTrain driveT, WPI_TalonSRX leftMotor, WPI_TalonSRX rightMotor, double distance, double speed){
        driveTrain = driveT;
        leftEncoder = new CTREMagEncoder(leftMotor);
        rightEncoder = new CTREMagEncoder(rightMotor);
        leftDistance = distance;
        rightDistance = distance;
        leftSpeed = speed;
        rightSpeed = speed;
    }

    public DriveDistance(DriveTrain driveT, WPI_TalonSRX leftMotor, WPI_TalonSRX rightMotor, double leftD, double rightD, double leftS, double rightS){
        driveTrain = driveT;
        leftEncoder = new CTREMagEncoder(leftMotor);
        rightEncoder = new CTREMagEncoder(rightMotor);
        leftDistance = leftD;
        rightDistance = rightD;
        leftSpeed = leftS;
        rightSpeed = rightS;
    }

    /**
     * Call every loop cycle to execute task
     * @return true if the task is running, false if the task is finished
     */
    public boolean processPeriodic(){
        if(state){
            if(leftEncoder.getPosition() < leftDistance || rightEncoder.getPosition() < rightDistance){

                double left = 0, right = 0;
                if(leftEncoder.getPosition() < leftDistance)
                    left = leftSpeed;
                if(rightEncoder.getPosition() < rightDistance)
                    right = rightSpeed;
                
                driveTrain.moveMotors(left, right, true);
                state = true;
            }
            else{
                driveTrain.moveMotors(0,0,true);
                state = false;
            }
        }
        return state;
    }

    /**
     * Starts the process
     */
    public void start(){
        state = true;
        //this.processPeriodic();
    }

    /**
     * Ends the process
     */
    public void stop(){
        state = false;
        //this.processPeriodic();
    }

    /**
     * Resets the function to its original state before it was originally called
     */
    public void reset(){
        leftEncoder.setCurrentPositionZero();
        rightEncoder.setCurrentPositionZero();
        //set every variable to 0?
    }

}

//current distance travelled left right
//if left is done right should also stop
//if both distances are met stop the method
