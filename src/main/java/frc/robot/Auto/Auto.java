/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Auto;

/**
 * Auto is used for handling all events that fall under the Automatic period of
 * the competition (2020 competition).
 * 
 *
 * 
 *
 */
public class Auto {

    private AutoProcess[] tasks;
    private int currentProcess;

    public Auto(AutoProcess[] toDo) {
        setCurrentProcess(0);
        tasks = toDo;
    }

    /**
     * Call this method in the main loop whenever you want auto to run
     */
    public void autoPeriodic()
    {
        int currentProcess = 0;
        do{
            tasks[currentProcess].processPeriodic();
            if(!tasks[currentProcess].getState())
                currentProcess++;
        }while(currentProcess < tasks.length);
    }

    /**
     * 
     * @return The index of the current process
     */
    public int getCurrentProcess() {
        return currentProcess;
    }

    /**
     * 
     * @param index The index of the process on which to run when autoPeriodic() is called. This will still call the rest of the functions after the new process finishes.
     */
    public void setCurrentProcess(int index) {
        currentProcess = index;
    }

    public void reset()
    {
        currentProcess = 0;
    }
}
