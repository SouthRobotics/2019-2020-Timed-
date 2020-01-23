/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Arduino;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;


public class ArduinoCommunication {

    private int baud_rate;
    private SerialPort serial;

    public ArduinoCommunication(int baud, Port port)
    {
        baud_rate = baud;
        serial = new SerialPort(baud, port);
    }

    public ArduinoCommunication(int baud)
    {
        baud_rate = baud;
        serial = new SerialPort(baud, SerialPort.Port.kUSB1);
    }

    public void sendString(String data)
    {
        serial.writeString(data);
    }

    public String readString()
    { //Returns a string from the serial port
        serial.setTimeout(2);
        return serial.readString();
    }

    public String readByte()
    { //Reads 1 byte of the Serial Data, returns null if there is an exception thrown
        try{
            return new String(serial.read(1), "UTF-8");
        }
        catch(Exception e){}
        
        return null;
    }

    public int getBaudRate()
    {
        return baud_rate;
    }

}
