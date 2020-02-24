/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Auto;


public abstract class TimedProcess extends AutoProcess {

    private double timeToExecute;

    public TimedProcess(double time){
        timeToExecute = time;
    }

    /**
     * Starts the process
     */
    public abstract void start();

    /**
     * Ends the process
     */
    public abstract void stop();

    /**
     * Resets the function to its original state before it was originally called
     */
    public abstract void reset();


    public void timerPeriodic(){
            if (super.getTimeElapsedSinceStart() > timeToExecute){
                stop();
            }

    }  
}
