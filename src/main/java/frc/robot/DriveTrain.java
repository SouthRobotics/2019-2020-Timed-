
package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
//NOTES:
/*
Many of these methods should "Return false if any errors", is a try-catch required?
*/

public class DriveTrain {
    //in case left and right are wrong (swapped), change this DOES NOT CHANGE MOTOR ORDER
    //public static final boolean SWAP_RIGHT_LEFT_WHEEL_SIDES = false;/*Code to swap left and right 1/2*/

    //should the speeds inputted into the tankDrive be squared? this may smoothen speed or something
    public static final boolean SQUARE_SPEEDS = true;    
    public static final int NUMBER_OF_SPEED_CONTROLLERS = 2;

    private boolean moveEnabled = true;//can the drivetrain move at all? default is true
    private double speedMultiplier;
    private DifferentialDrive diffDrive;//obj reference
    private OI input;//obj reference
    private SpeedControllerGroup[] speedControllers;//even index = left, odd index = right
    private WPI_TalonSRX[] motors;//first ones are left, then right IF NOT SWAPPED

    /** 
    * @param leftMotors The array of ports of all the left-side motors for driving on the robot. Must be a valid port#
    * @param rightMotors The array of ports of all the right-side motors for driving on the robot. Must be a valid port#
    * @param OI Reference to the OI class. This is required so that the DriveTrain's move methods know the input (OI.getSpeeds())
    * @param speedMultiplier A multiplier to the speed of all driving done by this DriveTrain. This can be ignored by calling moveMotors(--, --, false);
    * @return Returns a new instance of the DriveTrain class
    */
    public DriveTrain(int[] leftMotors, int[] rightMotors, OI input, double speedMultiplier){
        this.input = input;
        this.speedMultiplier = speedMultiplier;

        //////////////////SETUP DIFFRENTIAL DRIVE, SPEEDCONTROLLERGROUPS, AND TALONS/////////////
        this.speedControllers = new SpeedControllerGroup[NUMBER_OF_SPEED_CONTROLLERS];
        this.motors = new WPI_TalonSRX[leftMotors.length + rightMotors.length];
        WPI_TalonSRX[] tempR = new WPI_TalonSRX[rightMotors.length];
        WPI_TalonSRX[] tempL = new WPI_TalonSRX[leftMotors.length];

        for(int i = 0; i < leftMotors.length; i++){
            tempL[i] = new WPI_TalonSRX(leftMotors[i]);
            motors[i] = tempL[i];
        }
        speedControllers[0] = new SpeedControllerGroup(tempL[0], tempL[1], tempL[2]);

        for(int i = 0; i < rightMotors.length; i++){
            tempR[i] = new WPI_TalonSRX(rightMotors[i]);
            motors[leftMotors.length + i] = tempR[i];
        }
        speedControllers[1] = new SpeedControllerGroup(tempR[0], tempR[1], tempR[2]);

        /*Code to swap left and right 2/2*/
        // if(SWAP_RIGHT_LEFT_WHEEL_SIDES){// swap if the sides are opposite DOES NOT SWAP MOTORS ARRAY ORDER
        //     SpeedControllerGroup temp = speedControllers[1];
        //     speedControllers[1] = speedControllers[0];
        //     speedControllers[0] = temp;
        // }
        
        this.diffDrive = new DifferentialDrive(speedControllers[0], speedControllers[1]);//left, then right

        //////////////////END OF MOTORS AND CLASS SETUP//////////////////////
    }

    //////////////MOVEMENT////////////////
    //Modes: Joysticks = 0, GTADrive = 1, ArcadeDrive = 2 WARNING!!! SET TO 0 BY DEFAULT
    
    //read OI (input) speeds and feed it into main moveMotors
    /** 
     * Sets the motor speeds as long as moveEnabled == true. Uses the speedMultiplier of this DriveTrain and speeds from OI.
     * <br><br>For greater control, use moveMotors(double leftSpeed, double rightSpeed, boolean speedMultiplier)
    */
    public void moveMotors(){
        double[] temp = input.getSpeeds();
        moveMotors(temp[0], temp[1], true);
    };

    //main moveMotors, sets the motors (using DiffrentialDrive) to speeds, can use global speed multiplier or not
    /** 
     * Sets the motor speeds as long as moveEnabled == true
    * @param leftSpeed The speed of the left side. Magnitude (after multiplying by private speedMultiplier, possibly) should be 1 or less
    * @param rightSpeed The speed of the right side. Magnitude (after multiplying by private speedMultiplier, possibly) should be 1 or less
    * @param speedMultiplier Should the speedMultiplier of this DriveTrain be effective?
    */
    public void moveMotors(double leftSpeed, double rightSpeed, boolean speedMultiplier){
        //speed wil be multiplied by this.speedMultiplier if boolean speedMultiplier is true, otherwise, 1
        if(!moveEnabled) 
            return;
        double speedMult = speedMultiplier ? this.speedMultiplier : 1;
        if(input.getControlMode() != 2)
            diffDrive.tankDrive(leftSpeed * speedMult, rightSpeed * speedMult, SQUARE_SPEEDS);
        else
            diffDrive.arcadeDrive(leftSpeed, rightSpeed, true);
    }
    //1 method to do all of tankDrive, gtaDrive, arcadeDrive
    /** 
     * Attempts to set the motor speeds according to the same driving mode and this DriveTrain's OI's input values
     * <br><br>0 = Tank
     * <br><br>1 = GTA
     * <br><br>2 = Arcade
    * @param mode The mode to use. See above, only input a mode number in the range [0, 2]
    * @return Returns true if the speed was set successfully. Causes for failure/false include OI's control mode was different. 
    */
    public boolean checkMode(int mode){
        try{
            if(input.getControlMode() == mode && moveEnabled){
                moveMotors();
                return true;
            }
            else{
                return false;
            }
        }catch(Exception e){//perhaps this should print to console
            return false;
        }
    }

    public WPI_TalonSRX[] getMotors()
    {
        return motors;
    }
    //basically moveMotors(); but checks if mode is right and returns if wrong mode or error
    /** 
     * Attempts to set the motor speeds according to the Tank driving mode and this DriveTrain's OI's input values
    * @return Returns true if the speed was set successfully. Causes for failure/false include OI's control mode was different. 
    */
    public boolean tankDrive(){return checkMode(0);}

    //basically moveMotors(); but checks if mode is right and returns if wrong mode or error
    /** 
     * Attempts to set the motor speeds according to the GTA driving mode and this DriveTrain's OI's input values
    * @return Returns true if the speed was set successfully. Causes for failure/false include OI's control mode was different. 
    */
    public boolean gtaDrive(){return checkMode(1);}

    //basically moveMotors(); but checks if mode is right and returns if wrong mode or error
    /** 
     * Attempts to set the motor speeds according to the Arcade driving mode and this DriveTrain's OI's input values
    * @return Returns true if the speed was set successfully. Causes for failure/false include OI's control mode was different. 
    */
    public boolean arcadeDrive(){return checkMode(2);}
    
    //////////////////END MOVEMENT/////////////////
    ////////////////GET////////////
    /** 
     * At time of writing, indexes 0-2 are left and 3-5 are right
    * @param index the inded in the private motors array. Must be a valid index in the motors array
    * @return Returns the speed of a motor (Talon) from the private motors array by index
    */
    public double getMotorSpeed(int index){return motors[index].get();}
    /** 
    * @return Returns the current speed of the left side
    */
    public double getLeftSpeed(){return speedControllers[0].get();}
    /** 
    * @return Returns the current speed of the right side
    */
    public double getRightSpeed(){return speedControllers[1].get();}
    /** 
     * Indexes:
     * <br><br>0 = left
     * <br><br>1 = right
    * @param index The index of the SpeedControllerGroup in the private array speedControllers. Must be a valid index in the speedControllers array
    * @return Returns a SpeedGroupControllerGroup by index in the private speedControllers array
    */
    public SpeedControllerGroup getSpeedControllerGroup(int index){ return speedControllers[index];}
    /** 
    * @return Returns the DriveTrain's private DifferentialDrive variable
    */
    public DifferentialDrive getDifferentialDrive(){return diffDrive;}
    /** 
    * At time of writing, indexes 0-2 are left and 3-5 are right
    * @param index the inded in the private motors array. Must be a valid index in the motors array
    * @return Returns a motor (Talon) from the private motors array by index
    */
    public WPI_TalonSRX getMotorByIndex(int index){return motors[index];}
    /** 
    * @param port The port that the Talon is connected to. Must be a valid port#
    * @return Returns a motor (Talon) with that port, from the private motors array. Prints a message if no motor with that port is in the private motors array
    */
    public WPI_TalonSRX getMotorByPort(int port){
        for(WPI_TalonSRX m : motors){ 
            if(m.getDeviceID()==port) return m;
        }

        System.out.println("Attempted to get motor by port, but no motors in motors array have this port");
        return null;
    }
    ////////////////////SET/////////////////////
    /** 
    * @param enable Should this DriveTrain be enabled or not? Disabled will not execute any movement
    */
    public void setEnable(boolean enable){moveEnabled = enable;}
}
