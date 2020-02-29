/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Auto;

import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public abstract class AutoProcess {

    protected boolean state = false;
    protected Timer timeElapsed;

    /**
     * Call every loop cycle to execute task
     * @return true if the task is running, false if the task is finished
     */
    public abstract boolean processPeriodic();

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

    /**
     * Gets the time since the process started to run
     * @return Time elapsed in seconds
     */
    public double getTimeElapsedSinceStart()
    {
        return timeElapsed.get();
    }

    public boolean getState()
    {
        return state;
    }

}
