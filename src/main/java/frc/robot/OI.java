/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Do NOT add any static variables to this class, or any initialization at all.
 * Unless you know what you are doing, do not modify this file except to
 * change the parameter class to the startRobot call.
 */
public class OI {
    //private vals to be used in the class
    public static final int XBOX_LEFT_X_AXIS = 0;
    public static final int XBOX_LEFT_Y_AXIS = 1;
    public static final int XBOX_RIGHT_X_AXIS = 4;
    public static final int XBOX_RIGHT_Y_AXIS = 5;
    public static final int XBOX_X_BUTTON = 1;
    public static final int XBOX_A_BUTTON = 2;
    public static final int XBOX_B_BUTTON = 3;
    public static final int XBOX_Y_BUTTON = 4;
    public static final int XBOX_LEFT_BUMPER = 5;
    public static final int XBOX_RIGHT_BUMPER = 6;
    public static final int XBOX_RIGHT_JOYSTICK_BUTTON = 10;
    public static final int XBOX_LEFT_JOYSTICK_BUTTON = 9;
    public static final int XBOX_VIEW = 7;
    public static final int XBOX_MENU = 8;
    public static final int XBOX_L_TRIGGER = 2;
    public static final int XBOX_R_TRIGGER = 3;
    
    public static final int JOY_Button1 = 1;
    public static final int JOY_Button2 = 2;
    public static final int JOY_Button3 = 3;
    public static final int JOY_Button4 = 4;
    public static final int JOY_Button5 = 5;
    public static final int JOY_Button6 = 6;
    public static final int JOY_Button7 = 7;
    public static final int JOY_Button8 = 8;
    public static final int JOY_Button9 = 9;
    public static final int JOY_Button10 = 10;
    public static final int JOY_Button11 = 11;
    public static final int JOY_Button12 = 12;
    public static final int JOY_X = 0;
    public static final int JOY_Y = 1;

    /*
**************
    // controlModes: 0-DualJoystickTankDrive // 1-XboxGTADrive // 2-SingleJoyArcadeDrive
**************
    */
    private int controlMode;
    public static final int TANKDRIVE = 0;
    public static final int GTADRIVE = 1;
    public static final int ARCADEDRIVE = 2;
    private int driveStraightButton = -100;
    private int driveStraightController = -1;
    private int forBackdirection = 1;
    private int[] directionArray = {1,1};
    private Joystick[] joyArray;
    private XboxController xboxController;

    /**
     * @param ports the ports of each of the Joysticks
     */
    public OI(int[] ports){

        joyArray = new Joystick[ports.length];

        for(int i=0; i<ports.length;i++){
            joyArray[i] = new Joystick(ports[i]);
        }
        controlMode = (ports.length > 1) ? 0:2;
    }

    /**
     * @param ports the ports of each of the Joysticks
     * @param button the button, that when pushed, makes the robot drive straight
     * @param controller the controller the afformentioned button is on
     */
    public OI(int[] ports, int button, double controller){

        joyArray = new Joystick[ports.length];

        for(int i=0; i<ports.length;i++){
            joyArray[i] = new Joystick(ports[i]);
        }
        controlMode = (ports.length > 1) ? 0:2;
        driveStraightButton = button;
        driveStraightController = Integer.parseInt(Double.toString(controller));
    }

    /**
     * @param port The Xbox Controller Port
     */
    public OI(int port){
        xboxController = new XboxController(port);
        controlMode=1;
    }

    /**
     * @param ports the ports of each of the Joysticks
     * @param port The Xbox Controller Port
     * @param mode the desired driving mode
     */
    public OI(int[] ports, int port, int mode){

        joyArray = new Joystick[ports.length];

        for(int i=0; i<ports.length;i++){
            joyArray[i] = new Joystick(ports[i]);
        }
        xboxController = new XboxController(port);

        controlMode = mode;
    }

    /**
     * @return Returns the left and right speed for driving based on current driving mode except for arcade which returns direction in [0] and rotation in [1]
     */
    public double[] getSpeeds(){
        double[] speedsArray = new double[2];
        if(controlMode==0){ //Dual Joy
            if (driveStraightController != -1 && joyArray[driveStraightController].getRawButton(driveStraightButton)){
                for(int i=0;i<speedsArray.length;i++){
                    speedsArray[i] = joyArray[driveStraightController].getRawAxis(JOY_Y)*forBackdirection*directionArray[i];
                }
            }
            else{
                for(int i=0;i<speedsArray.length;i++){
                    speedsArray[i] = joyArray[i].getRawAxis(JOY_Y)*forBackdirection*directionArray[i];
                }
            }
        }

        else if(controlMode==1){//gta 
            speedsArray[0] = (xboxController.getRawAxis(XBOX_L_TRIGGER) - xboxController.getRawAxis(XBOX_R_TRIGGER))*forBackdirection*directionArray[0];
            speedsArray[1] = (xboxController.getRawAxis(XBOX_L_TRIGGER) - xboxController.getRawAxis(XBOX_R_TRIGGER))*forBackdirection*directionArray[1];
            if (xboxController.getRawAxis(XBOX_LEFT_X_AXIS)>0){
                speedsArray[0] = speedsArray[1]*(1-xboxController.getRawAxis(XBOX_LEFT_X_AXIS));
            }
            else if (xboxController.getRawAxis(XBOX_LEFT_X_AXIS)<0){
                speedsArray[1] = speedsArray[0]*(1+xboxController.getRawAxis(XBOX_LEFT_X_AXIS));
            }
        }

        else if(controlMode==2){//arcade 
            speedsArray[0] = joyArray[0].getRawAxis(JOY_Y)*forBackdirection;
            speedsArray[1] = joyArray[0].getRawAxis(JOY_X);
        }
        return speedsArray;
    }

    /**
     * @return returns the array of joystick objects
     */
    public Joystick[] getJoysticks(){
        return joyArray;
    }

    /**
     * @return returns the XboxController object
     */
    public XboxController getXboxController(){
        return xboxController;
    }

    /**
     * @return returns the current control mode
     */
    public int getControlMode(){
        return controlMode;
    }

    /**
     * @return Switches the driving direction of the robot
     */
    public void switchDirections() {
        forBackdirection = forBackdirection*-1;

    }

    /**
     * @return returns the driving direction of the robot
     */
    public int getDirection() {
        return forBackdirection;

    }

    /**
     * @return Sets the driving direction (L-R) of the robot
     */
    public void setLRDirections(int[] dirs) {
        for (int i=0;i<directionArray.length;i++){
            directionArray[i] = dirs[i];
        }

    }

    /**
     * @return returns the driving (L-R) direction of the robot
     */
    public int[] getLRDirections() {
        return directionArray;

    }

    /**
     * @param button the # of the button to set the DriveStraightButton to
     * @param controller the controller the button is on
     * @return sets the driveStraightButton
     */
    public void setDriveStraightButton(int button, int controller) {
        driveStraightButton = button;
        driveStraightController = controller;

    }    
    
    /**
    * @return gets the DriveStraigh Button and controller
    */
    public int[] getDriveStraightButton() {
        int[] temp = {driveStraightButton, driveStraightController};
        return temp;

    }


    /**
     * @param button the # of the button to return
    * @return returns if a certain button has been pressed (if more than one controller is present goes in order of intilaized joys)
    */
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

    /**
     * @param axis the # of the axis to return
    * @return get the raw axis of the joysticks depending on the driving mode
    */
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
