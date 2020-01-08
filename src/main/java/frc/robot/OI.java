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
public class OI {
    //private vals to be used in the class
    private Joystick[] joyArray;
    private XboxController xboxController;
    // controlModes: 0-DualJoystickControl // 1-XboxGTADrive // 2-SingleJoyArcadeDrive
    private int controlMode;
    //class constructors
    //constructor for joy
    public OI(int[] ports){
        for(int i=0; i<ports.length;i++){
            joyArray[i] = new Joystick(ports[i]);
    }
    controlMode = (ports.length > 1) ? 0:2;
    }
    //constructor for gtadrive
    public OI(int port){
        xboxController = new XboxController(port);
        controlMode=1;
    }
    //constructor for other modes
    public OI(int[] ports, int port, int mode){
        for(int i=0; i<ports.length;i++){
            joyArray[i] = new Joystick(ports[i]);
        }
        xboxController = new XboxController(port);
        controlMode = mode;
    }
    //returns the left and right speed for driving based on current driving mode
    public double[] getSpeeds(){
        double[] speedsArray = new double[2];
        if(controlMode==0){
           for(int i=0;i<2;i++){
                speedsArray[i] = joyArray[i].getX();
            }
        }
        if(controlMode==1){//gta 
            speedsArray[0] = xboxController.getRawAxis(3) - xboxController.getRawAxis(2);
            speedsArray[1] = xboxController.getRawAxis(3) - xboxController.getRawAxis(2);
            if (xboxController.getRawAxis(2)>0){
                speedsArray[1] = speedsArray[1]*(1-xboxController.getRawAxis(2));
            }
            else if (xboxController.getRawAxis(2)<0){
                speedsArray[0] = speedsArray[0]*(1+xboxController.getRawAxis(2));
            }
        }
        if(controlMode==2){//arcade 
            speedsArray[0] = joyArray[0].getX();
            speedsArray[1] = joyArray[0].getX();
            if (joyArray[0].getY()>0){
                speedsArray[1] = speedsArray[1]*(1-joyArray[0].getX());
            }
            else if (joyArray[0].getY()<0){
                speedsArray[0] = speedsArray[1]*(1+joyArray[0].getX());
            }
        }
        return speedsArray;
    }

   //returns the array of joystick objects
    public Joystick[] getJoysticks(){
        return joyArray;
    }
    //returns the XboxController object
    public XboxController getXboxController(){
        return xboxController;
    }
    //returns the current control mode
    public int getControlMode(){
        return controlMode;
    }
    //returns if a certain button has been pressed (if more than one controller goes in order of intilaized joys)
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
    //get the raw axis of the joysticks depending on the driving mode
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
