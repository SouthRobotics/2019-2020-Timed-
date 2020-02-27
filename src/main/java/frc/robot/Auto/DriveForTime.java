package frc.robot.Auto;

import frc.robot.DriveTrain;

public class DriveForTime extends TimedProcess {

    private DriveTrain driveTrain;
    private double leftSpeed, rightSpeed;
    
    //constructors self-explanitory
    public DriveForTime(DriveTrain drivetrain, double time, double speed){ this(drivetrain, time, speed, speed); }
    public DriveForTime(DriveTrain drivetrain, double time, double leftSpeed, double rightSpeed){
        super(time);
        this.driveTrain = drivetrain;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
    }    

    @Override
    public boolean processPeriodic(){
        driveTrain.moveMotors(leftSpeed, rightSpeed, true);//while doing the task, move motors at this speed
        if(super.state == false){//if the task is done, stop the motors
            driveTrain.moveMotors(0, 0, true);
        }
        return super.state;//return if should stop or not
    }
}