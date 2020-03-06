/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Arduino;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;


public class ArduinoCommunication { //Class is used to communicate through the RoboRio's built in USB ports through the Arduino Serial Port

    private int baud_rate; //Can only be 9600 and 115200 due to RoboRio's constraints
    private SerialPort serial; //The serial object of the RoboRio
    public boolean isConnected;

    public ArduinoCommunication(int baud, Port port)
    {
        baud_rate = baud;
        try{
            serial = new SerialPort(baud, port);
            isConnected = true;
        }catch(Exception e)
        {
            isConnected = false;
        }
    }

    public ArduinoCommunication(int baud)
    {
        baud_rate = baud;
        try{
            serial = new SerialPort(baud, SerialPort.Port.kUSB1);
            isConnected = true;
        }catch(Exception e)
        {
            isConnected = false;
        }
    }

    public void sendString(String data) //Sends a string to the arduino
    {
        if(isConnected)
            serial.writeString(data);
    }

    public String readString() //Reads a string from the SerialPort
    { //Returns a string from the serial port
        if(isConnected)
        {
            serial.setTimeout(2);
            return serial.readString();
        }
        else
        {
            return "";
        }
    }

    public String readByte()
    { //Reads 1 byte of the Serial Data, returns null if there is an exception thrown
        if(isConnected)
        {
            try{
                return new String(serial.read(1), "UTF-8");
            }
            catch(Exception e){}

            return "";
        }
        else
            return "";
    }

    public int getBaudRate() ///Returns the baud rate. Used to keep things consistent
    {
        return baud_rate;
    }


    //Documentation for the following map methods: https://www.arduino.cc/reference/tr/language/functions/math/map/
    public static double map(long x, long in_min, long in_max, long out_min, long out_max) //Standard arduino function that is useful that I figured I would bring here.
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static double map(int x, int in_min, int in_max, int out_min, int out_max) //Standard arduin functiono that is useful that I figured I would bring here
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static double map(double x, double in_min, double in_max, double out_min, double out_max) //Standard arduino function that is useful that I figured I would bring here
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

}
