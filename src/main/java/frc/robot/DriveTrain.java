import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
//NOTES:
/*

*/

public class DriveTrain {
    public static final boolean SWAP_RIGHT_LEFT_WHEEL_SIDES = false;//in case left and right are wrong (swapped), change this
    public static final boolean SQUARE_SPEEDS = false;//should the speeds inputted into the tankDrive be squared? this may smoothen speed or something
    public static final int NUMBER_OF_SPEED_CONTROLLERS = 2;

    public static int ctrlMode = 0;//Joysticks = 0, GTADrive = 1, ArcadeDrive = 2 WARNING!!! SET TO 0 BY DEFAULT IN CONSTRUCTOR

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

    void moveMotors(double leftSpeed, double rightSpeed, boolean speedMultiplier){
        //speed wil be multiplied by this.speedMultiplier if boolean speedMultiplier is true, otherwise, 1
        double speedMult = speedMultiplier ? this.speedMultiplier : 1;
        diffDrive.tankDrive(leftSpeed * speedMult, rightSpeed * speedMult, SQUARE_SPEEDS);
    }
}