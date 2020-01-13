import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
//NOTES:
/*
moveMotors() does not "Returns true or false if movement was successful" because it is void type
Unsure about moveMotors() with no arguments "Uses whatever the mode is to move the motors." Is a simple tankDrive() ok?

Should tankDrive and gtaDrive throw errors if in wrong mode??

Many of these methods should "Return false if any errors", is a try-catch required?

getMotorSpeed changed to double from double[], because it does not make sence
*/

public class DriveTrain {
    public static final boolean SWAP_RIGHT_LEFT_WHEEL_SIDES = false;//in case left and right are wrong (swapped), change this
    public static final boolean SQUARE_SPEEDS = false;//should the speeds inputted into the tankDrive be squared? this may smoothen speed or something
    public static final int NUMBER_OF_SPEED_CONTROLLERS = 2;

    private int ctrlMode = 0;//Joysticks = 0, GTADrive = 1, ArcadeDrive = 2 WARNING!!! SET TO 0 BY DEFAULT
    private boolean moveEnabled = true;//can the drivetrain move at all? default is true

    private double speedMultiplier;
    private DifferentialDrive diffDrive;
    private OI input;
    private SpeedControllerGroup[] speedControllers;//even = left, odd = right
    private WPI_TalonSRX[] motors;//first ones are left, then right

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

        if(SWAP_RIGHT_LEFT_WHEEL_SIDES){// swap if the sides are opposite
            SpeedControllerGroup temp = speedControllers[1];
            speedControllers[1] = speedControllers[0];
            speedControllers[0] = temp;
        }
        
        this.diffDrive = new DifferentialDrive(speedControllers[0], speedControllers[1]);//left, then right
        //////////////////END OF MOTORS SETUP AND STUFF//////////////////////
    }

    void moveMotors(){return moveMotors(OI.speedLeftThingy, OI.speedRightThingy, true);};
    void moveMotors(double leftSpeed, double rightSpeed, boolean speedMultiplier){
        //speed wil be multiplied by this.speedMultiplier if boolean speedMultiplier is true, otherwise, 1
        if(!moveEnabled) return;
        double speedMult = speedMultiplier ? this.speedMultiplier : 1;
        diffDrive.tankDrive(leftSpeed * speedMult, rightSpeed * speedMult, SQUARE_SPEEDS);
    }
    boolean tankDrive(){
        if(THERE_IS_NOT_XBOX){
            moveMotors();
            return true;
        }else{
            return false;
        }
    }
    boolean gtaDrive(){
        if(THERE_IS_NOT_XBOX){
            return false;
        }else{
            moveMotors();
            return true;
        }
    }
    boolean arcadeDrive(){
        if(ctrlMode != 2){
            throw new Exception("Wrong mode for arcadeDrive. Mode (ctrlMode) is " + ctrlMode + " but expected 2");
            return false;//unreachable code?? is it reachable after an Exception?
        }
        if(THERE_IS_NOT_XBOX){
            moveMotors();
            return true;
        }else{
            return false;
        }
    }

    //danger - make sure index is right
    double getMotorSpeed(int index){
        return motors[index].get();
    }

    double getLeftSpeed(){return speedControllers[0].get();}
    double getRightSpeed(){return speedControllers[1].get();}

    void setEnable(boolean enable){moveEnabled = enable;}
    void setMode(int mode){ctrlMode = mode;}
}