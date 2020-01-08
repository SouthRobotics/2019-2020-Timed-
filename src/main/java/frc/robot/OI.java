/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Do NOT add any static variables to this class, or any initialization at all.
 * Unless you know what you are doing, do not modify this file except to
 * change the parameter class to the startRobot call.
 */
public final class OI {
    //private vals to be used in the class
    private Joystick[] joyArray;
    private XboxController xboxController;
    // controlModes: 0-DualJoystickControl // 1-XboxGTADrive // 2-SingleJoyArcadeDrive
    private int controlMode;
    //class constructors
    public OI(int[] ports){
        for(int i=0; i<ports.length;i++){
            joyArray[i] = new Joystick(ports[i]);
    }
    controlMode = (ports.length > 1) ? 0:2;
    }
    public OI(int port){
        xboxController = new XboxController(port);
        controlMode=1;
    }
    public OI(int[] ports, int port, int mode){
        for(int i=0; i<ports.length;i++){
            joyArray[i] = new Joystick(ports[i]);
        }
        xboxController = new XboxController(port);
        controlMode = mode;
    }
    public double[] getSpeeds(){
        double[] speedsArray = new double[2];
        if(controlMode==0){
           for(int i=0;i<2;i++){
                speedsArray[i] = joyArray[i].getX();
            }
        }
        if(controlMode==1){//gta 
            speedsArray[0] = (xboxController.getRawAxis(3)>1)?
                xboxController.getRawAxis(3):xboxController.getRawAxis(2);
            speedsArray[1] = (xboxController.getRawAxis(3)>1)?
                xboxController.getRawAxis(3):xboxController.getRawAxis(2);
            if (xboxController.getRawAxis(2)>1){
                speedsArray[1] -=xboxController.getRawAxis(2);
            }
            else if (xboxController.getRawAxis(2)<1){
                speedsArray[0] += xboxController.getRawAxis(2);
            }
        }
        if(controlMode==2){//arcade 0-throttle 1-rotation to be used w/ arcade drive
            speedsArray[0] = joyArray[0].getX();
            speedsArray[1] = joyArray[0].getX();
            if (joyArray[0].getY()>1){
                speedsArray[1] -=joyArray[0].getY();
            }
            else if (joyArray[0].getY()<1){
                speedsArray[0] += joyArray[0].getY();
            }
        }
        return speedsArray;
    }

   
    public Joystick[] getJoysticks(){
        return joyArray;
    }
    public XboxController getXboxController(){
        return xboxController;
    }
    public int getControlMode(){
        return controlMode;
    }
    public boolean[] getButton(int button){
        int numofButs = (controlMode>0)?(controlMode==1)?1:1:joyArray.length;
        boolean[] butArray = new boolean[numofButs];
        if(controlMode==0){
           for(int i=0;i<joyArray.length;i++){
            butArray[i] = joyArray[i].getRawButton(button);
            }
        }
        if(controlMode==1){
            butArray[0] = xboxController.getRawButton(button);
        }
        if(controlMode==2){
            butArray[0] = joyArray[0].getRawButton(button);
        }
        return butArray;
    }
    public double[] getRawAxis(int axis){
        int numofaxis = (controlMode>0)?(controlMode==1)?3:2:joyArray.length;
        double[] axisarray = new double[numofaxis];
        if(controlMode==0){
           for(int i=0;i<joyArray.length;i++){
                axisarray[i] = joyArray[i].getRawAxis(axis);
            }
        }
        if(controlMode==1){
            axisarray[0] = xboxController.getRawAxis(axis);
        }
        if(controlMode==2){
            axisarray[0] = joyArray[0].getRawAxis(axis);
        }
        return axisarray;
    }

}
