/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Arduino;

import edu.wpi.first.wpilibj.SerialPort.Port;
import frc.robot.Arduino.ArduinoCommunication;

/**
 * Pixy class is used with ArduinoCommunication class
 * 
 * Takes a message from ArduinoCommunication class and decodes it 
 *      Format of message: $x-value:y-value:width:height%
 * 
 */
public class PixyCam {


    double x, y, w, h;

    ArduinoCommunication arduino;

    public PixyCam(int baud, Port port)
    {
        x = 0; y = 0; w = 0; h = 0;
        arduino = new ArduinoCommunication(baud, port);
    }

    public PixyCam(int baud)
    {
        arduino = new ArduinoCommunication(baud, Port.kUSB1);
    }

    public double[] getBlocks()
    {
        String temp = arduino.readString();
        if(temp.indexOf("&") < 0)
        {
            if(temp.indexOf("x") != -1 && temp.indexOf("x") <= temp.length()-3)
                x = Integer.parseInt(temp.substring(temp.indexOf("x") + 1, temp.indexOf("x") + 3)) / 100.0;
            
            if(temp.indexOf("y") != -1 && temp.indexOf("y") <= temp.length()-3)
                y = Integer.parseInt(temp.substring(temp.indexOf("y") + 1, temp.indexOf("y") + 3)) / 100.0;
            
            if(temp.indexOf("w") != -1 && temp.indexOf("w") <= temp.length()-3)
                w = Integer.parseInt(temp.substring(temp.indexOf("w") + 1, temp.indexOf("w") + 3)) / 100.0;

            if(temp.indexOf("h") != -1 && temp.indexOf("h") <= temp.length()-3)
                h = Integer.parseInt(temp.substring(temp.indexOf("h") + 1, temp.indexOf("h") + 3)) / 100.0;
        }
        else
        {
            x= -1; y= -1; w= -1; h= -1;
        }
        
        return new double[]{x,y,w,h};
    }
}
