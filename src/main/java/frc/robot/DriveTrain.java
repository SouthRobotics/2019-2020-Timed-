import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
//NOTES:
/*
Many of these methods should "Return false if any errors", is a try-catch required?
*/

public class DriveTrain {
    //in case left and right are wrong (swapped), change this DOES NOT CHANGE MOTOR ORDER
    public static final boolean SWAP_RIGHT_LEFT_WHEEL_SIDES = false;
    //should the speeds inputted into the tankDrive be squared? this may smoothen speed or something
    public static final boolean SQUARE_SPEEDS = false;    
    public static final int NUMBER_OF_SPEED_CONTROLLERS = 2;

    private boolean moveEnabled = true;//can the drivetrain move at all? default is true
    private double speedMultiplier;
    private DifferentialDrive diffDrive;//obj reference
    private OI input;//obj reference
    private SpeedControllerGroup[] speedControllers;//even = left, odd = right
    private WPI_TalonSRX[] motors;//first ones are left, then right IF NOT SWAPPED

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

        if(SWAP_RIGHT_LEFT_WHEEL_SIDES){// swap if the sides are opposite DOES NOT SWAP MOTORS ARRAY ORDER
            SpeedControllerGroup temp = speedControllers[1];
            speedControllers[1] = speedControllers[0];
            speedControllers[0] = temp;
        }
        
        this.diffDrive = new DifferentialDrive(speedControllers[0], speedControllers[1]);//left, then right
        //////////////////END OF MOTORS SETUP AND STUFF//////////////////////
    }

    //////////////MOVEMENT////////////////
    //Joysticks = 0, GTADrive = 1, ArcadeDrive = 2 WARNING!!! SET TO 0 BY DEFAULT
    
    //read OI (input) speeds and feed it into main moveMotors
    void moveMotors(){
        int[] temp = input.getSpeeds();
        moveMotors(temp[0], temp[1], true);
    };

    //main moveMotors, sets the motors (using DiffrentialDrive) to speeds, can use global speed multiplier or not
    void moveMotors(double leftSpeed, double rightSpeed, boolean speedMultiplier){
        //speed wil be multiplied by this.speedMultiplier if boolean speedMultiplier is true, otherwise, 1
        if(!moveEnabled) 
            return;
        double speedMult = speedMultiplier ? this.speedMultiplier : 1;
        diffDrive.tankDrive(leftSpeed * speedMult, rightSpeed * speedMult, SQUARE_SPEEDS);
    }
    //1 method to do all of tankDrive, gtaDrive, arcadeDrive
    boolean checkMode(int mode){
        try{
            if(OI.getControlMode() == mode && moveEnabled){
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
    //basically moveMotors(); but checks if mode is right and returns if wrong mode or error
    boolean tankDrive(){return checkMode(0);}

    //basically moveMotors(); but checks if mode is right and returns if wrong mode or error
    boolean gtaDrive(){return checkMode(1);}

    //basically moveMotors(); but checks if mode is right and returns if wrong mode or error
    boolean arcadeDrive(){return checkMode(2);}
    
    //////////////////END MOVEMENT/////////////////
    ////////////////GET////////////
    double getMotorSpeed(int index){return motors[index].get();}
    double getLeftSpeed(){return speedControllers[0].get();}
    double getRightSpeed(){return speedControllers[1].get();}
    ////////////////////SET/////////////////////
    void setEnable(boolean enable){moveEnabled = enable;}
}
