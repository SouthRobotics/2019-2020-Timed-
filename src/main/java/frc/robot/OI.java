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
    private final int LEFT_X_AXIS = 0;
    private final int LEFT_Y_AXIS = 1;
    private final int L_TRIGGER = 2;
    private final int R_TRIGGER = 3;
    private final int RIGHT_X_AXIS = 4;
    private final int RIGHT_Y_AXIS = 5;
    private final int JOY_X = 0;
    private final int JOY_Y = 1;
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
            if (joyArray[1].getRawButton(1)){
                for(int i=0;i<speedsArray.length;i++){
                    speedsArray[i] = joyArray[1].getRawAxis(JOY_X);
                }
            }
            else{
                for(int i=0;i<speedsArray.length;i++){
                    
                    speedsArray[i] = joyArray[i].getRawAxis(JOY_X);
                }
            }
        }
        if(controlMode==1){//gta 
            speedsArray[0] = xboxController.getRawAxis(R_TRIGGER) - xboxController.getRawAxis(L_TRIGGER);
            speedsArray[1] = xboxController.getRawAxis(R_TRIGGER) - xboxController.getRawAxis(L_TRIGGER);
            if (xboxController.getRawAxis(LEFT_Y_AXIS)>0){
                speedsArray[1] = speedsArray[1]*(1-xboxController.getRawAxis(LEFT_Y_AXIS));
            }
            else if (xboxController.getRawAxis(LEFT_Y_AXIS)<0){
                speedsArray[0] = speedsArray[0]*(1+xboxController.getRawAxis(LEFT_Y_AXIS));
            }
        }
        if(controlMode==2){//arcade 
            speedsArray[0] = joyArray[0].getRawAxis(JOY_X);
            speedsArray[1] = joyArray[0].getRawAxis(JOY_Y);
            if (joyArray[0].getRawAxis(JOY_Y)>0){
                speedsArray[1] = speedsArray[1]*(1-joyArray[0].getRawAxis(JOY_X));
            }
            else if (joyArray[0].getRawAxis(JOY_Y)<0){
                speedsArray[0] = speedsArray[1]*(1+joyArray[0].getRawAxis(JOY_X));
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
        int numofaxis = (controlMode>0)?(controlMode==1)?1:1:joyArray.length;
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
