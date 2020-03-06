/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.SpecificFunctions;

/**
 * Add your docs here.
 */
public class BallSystem {

    private Intake intake;
    private Outtake outtake;
    private OuttakeLift lift;

    /**
     * 
     * @param i
     * @param o
     * @param l
     */
    public BallSystem(Intake i, Outtake o, OuttakeLift l)
    {
        intake = i;
        outtake = o;
        lift = l;
    }

    public void ballSystemPeriodic()
    {
        if(outtake.outTakePeriodic())
        {
            intake.stop();
            lift.set(true);
        }
        else
        {
            if(!intake.intakePeriodic())
                lift.set(true);
            else
            {
                lift.set(false);
            }
        }                
    }
}
