/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.;

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
/**
   * Main initialization function. Do not perform any initialization here.
   *
   * <p>If you change your main robot class, change the parameter type.
   */

}
