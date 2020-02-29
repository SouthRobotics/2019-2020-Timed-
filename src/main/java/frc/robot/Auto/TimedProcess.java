/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Auto;


public abstract class TimedProcess extends AutoProcess {

    private double timeToExecute;

    /**
     * Starts the process, or resumes if it was previously stopped
     */
    public void start(){
        timeElapsed.start();
        state = true;
    }

    /**
     * Ends the process
     */
    public void stop(){
        timeElapsed.stop();
        state = false;
    }

    /**
     * Resets the function to its original state before it was originally called
     */
    public void reset(){
        timeElapsed.reset();
    }

    public boolean processPeriodic()
    {
        if(timeElapsed.get() >= timeToExecute)
        {
            this.stop();
        }
        else
        {
            state = true;
            timerPeriodic();
        }

        return state;
    }

    /**
     * Call this periodically for what you want to run when the timer is active
     */
    public abstract void timerPeriodic(); 
}
