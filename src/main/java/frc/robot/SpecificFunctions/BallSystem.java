/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.SpecificFunctions;

import frc.robot.OI;

/**
 * Add your docs here.
 */
public class BallSystem {

    private Intake intake;
    private Outtake outtake;
    private OuttakeLift lift;
    OI oi;
    int button, index;

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

    public BallSystem(Intake i, Outtake o, OuttakeLift l, OI inputs, int buttonNum, int buttonIndex)
    {
        intake = i;
        outtake = o;
        lift = l;
        oi = inputs;
        button = buttonNum;
        index = buttonIndex;
    }

    public void ballSystemPeriodic()
    {
        if(outtake.outTakePeriodic())
        {
            intake.pause();
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
        
        if(oi != null)
        {
            if(oi.getButton(button)[index])
            {
                lift.set(false);
            }
            else
            {
                lift.set(true);
            }
        }
    }
}
