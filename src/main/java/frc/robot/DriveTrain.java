import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;


public class DriveTrain {
    public static final boolean SWAP_RIGHT_LEFT_WHEEL_SIDES = false;//in case left and right are wrong (swapped), change this
    public static final int NUMBER_OF_SPEED_CONTROLLERS = 2;

    public static int ctrlMode;//Joysticks = 0, GTADrive = 1, ArcadeDrive = 2

    private boolean moveEnabled;
    private double speedMultiplier;
    private DifferentialDrive diffDrive;
    private OI input;
    private SpeedControllerGroup[] speedControllers;//even = left, odd = right
    private WPI_TalonSRX[] motors;

    public DriveTrain(int[] leftMotors, int[] rightMotors, OI input, double speedMultiplier){
        this.input = input;
        this.speedMultiplier = speedMultiplier;
        speedControllers = new SpeedControllerGroup[NUMBER_OF_SPEED_CONTROLLERS];
        WPI_TalonSRX[] tempR = new WPI_TalonSRX[rightMotors.length];
        WPI_TalonSRX[] tempL = new WPI_TalonSRX[leftMotors.length];

        for(int i = 0; i < leftMotors.length; i++){
            tempL[i] = new WPI_TalonSRX(leftMotors[i]);
        }
        speedControllers[0] = new SpeedControllerGroup(tempL);

        for(int i = 0; i < rightMotors.length; i++){
            tempR[i] = new WPI_TalonSRX(rightMotors[i]);
        }
        speedControllers[1] = new SpeedControllerGroup(tempR);

        if(SWAP_RIGHT_LEFT_WHEEL_SIDES){// swap if the sides are opposite
            SpeedControllerGroup temp = speedControllers[1];
            speedControllers[1] = speedControllers[0];
            speedControllers[0] = temp;
        }
        
        diffDrive = new DifferentialDrive(speedControllers[0], speedControllers[1]);//left, then right
    }
}