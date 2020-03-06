/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.Arduino; //Located in a folder called Arduino, change that if you need to do that

import edu.wpi.first.wpilibj.SerialPort.Port;
import frc.robot.Arduino.ArduinoCommunication;
import edu.wpi.first.wpilibj.Servo;

/**
 * Pixy class is used with ArduinoCommunication class
 * 
 * Takes a message from ArduinoCommunication class and decodes it 
 *      Format of message: x###y###w###h###
 * Also uses servo motors for pan and tilt. SERVOS ARE NOT CONNECTED TO THE PIXY. The servos are connected straight to the roborio's PWM channels
 * 
 * Pixy is connected to Arduino through the I2C port.
 */
public class PixyCam {


    double x, y, w, h; //It is the x, y, width and height of the tracked object

    ArduinoCommunication arduino; //The pixy should be attatched to the Arduino

    Servo pan, tilt; //Servo motors for the pan tilt mount, OPTIONAL. NOTE: Servos are not connected to the pixy, rather straight to the roborio through the PWM ports

    public PixyCam(int baud, Port port)
    {
        x = 0; y = 0; w = 0; h = 0;
        arduino = new ArduinoCommunication(baud, port);
        pan = null;
        tilt = null;
    }

    /** 
    * @param parameter The parameter.
    
    * @return What the method returns.
    * 
    */

    public PixyCam(int baud)
    {
        arduino = new ArduinoCommunication(baud, Port.kUSB1);
        pan = null;
        tilt = null;
    }

    public PixyCam(int baud, Port port, int panChannel, int tiltChannel)
    {
        x = 0; y = 0; w = 0; h = 0;
        arduino = new ArduinoCommunication(baud, port);
        pan = new Servo(panChannel);
        tilt = new Servo(tiltChannel);
    }

    public PixyCam(int baud, int panChannel, int tiltChannel)
    {
        arduino = new ArduinoCommunication(baud, Port.kUSB1);
        pan = new Servo(panChannel);
        tilt = new Servo(tiltChannel);
    }

    public double[] getBlocks() //Reads info from Arduino. ONLY ACCEPTS ONE BLOCK. It receives Serial Data in the format of x###y###w###h### and it will recieve one & if there is no data to be recieved
    { //all numbers returned are in the range of 0 to 1
        String temp = arduino.readString();
        if(temp.indexOf("&") < 0) //everything inside of this is just parsing data in the format in the comment above
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
            x= -1; y= -1; w= -1; h= -1; //Sets to -1 if there is no found objects to track
        }
        
        return new double[]{x,y,w,h};//Returns the info of the x,y,w,h
    }

    public void setServos(double pan_, double tilt_) //Values from -1 to 1, sets the servos of the pan tilt mount
    {
        if(pan != null && tilt != null) //This line makes it so this method can be called without errors if servos are not instantiated
        {
            pan_ = ArduinoCommunication.map(pan_, -1, 1, 0, 1);
            tilt_ = ArduinoCommunication.map(tilt_, -1, 1, 0, 1);
            pan.set(Math.abs(pan_));
            tilt.set(Math.abs(tilt_));
        }
    }

    public void setServos(double pan_, double tilt_, int numDecimalPlaces) //Values from -1 to 1, numDecimalPlaces will truncate decimal
    {
        if(pan != null && tilt != null) //This line makes it so this method can be called without errors if servos are not instantiated
        {
            pan_ = ((int)(pan_ * (Math.pow(10, numDecimalPlaces))) / (Math.pow(10, numDecimalPlaces)*1.0));//Makes it to a certain amount of decimal places
            tilt_ = ((int)(tilt_ * (Math.pow(10, numDecimalPlaces))) / (Math.pow(10, numDecimalPlaces)*1.0));//same as above
            pan_ = ArduinoCommunication.map(pan_, -1, 1, 0, 1);
            tilt_ = ArduinoCommunication.map(tilt_, -1, 1, 0, 1);
            pan.set(Math.abs(pan_));
            tilt.set(Math.abs(tilt_));
        }
    }

    public boolean isObjectFound() //Returns true if the Pixy sees an object
    {
        return !(x==-1 && y==-1 && h == -1 && w == -1);
    }

    public void followObjectPeriodic(boolean resetPos)//Follows the object using the pan/tilt mount. If resetPos is true and an object is not seen it will return to 0,0. Else it will hold hold its current position
    {
        if(pan == null || tilt == null)
            return;
        
        getBlocks();

        if(resetPos && !isObjectFound())
            setServos(0, 0);
        else if(isObjectFound())
        {
            
            double p  = pan.getPosition();
            double t  = tilt.getPosition();

            p += .075*(.5 - x);
            t -= .09*(.5 - y);
            
            pan.setPosition(p);
            tilt.setPosition(t);
        }
    }

    public void followObjectPeriodic(int resetX, int resetY)
    {
        if(pan == null || tilt == null)
            return;
        
        getBlocks();

        if(!isObjectFound())
            setServos(resetX, resetY);
        else if(isObjectFound())
        {
            
            double p  = pan.getPosition();
            double t  = tilt.getPosition();

            p += .075*(.5 - x);
            t -= .09*(.5 - y);
            
            pan.setPosition(p);
            tilt.setPosition(t);
        }
    }

    /**
     * Used to check if the Pixy (and arduino) are connected
     * @return
     */
    public boolean isConnected()
    {
        return arduino.isConnected;
    }
}
