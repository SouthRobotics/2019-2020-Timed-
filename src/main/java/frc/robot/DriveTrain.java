import edu.wpi.first.wpilibj.drive.DifferentialDrive;


public class DriveTrain {

    public static int ctrlMode;//Joysticks = 0, GTADrive = 1, ArcadeDrive = 2
    public static boolean SWAP_RIGHT_LEFT_WHEEL_SIDES = false;//in case left and right are wrong (swapped), change this

    private double speedMultiplier;
    private DiffrentialDrive diffDrive;
    private OI input;
    private SpeedControllerGroup[] speedControllers;//even = left, odd = right
    private SpeedControllerGroup RSpeedCtrl;
    private SpeedControllerGroup LSpeedCtrl;
    private Talon[] motors;

    public DriveTrain(int[] leftMotors, int[] rightMotors, OI input, double speedMultiplier){
        this.input = input;
        this.speedMultiplier = speedMultiplier;
        speedControllers = new SpeedControllerGroup[2];
        diffDrive = new DiffrentialDrive();//todo
        Talon[] tempR = new Talon[rightMotors.length];
        Talon[] tempL = new Talon[leftMotors.length];

        for(int i = 0; i < leftMotors.length; i++){
            tempL[i] = new Talon(leftMotors[i]);
        }
        speedControllers[0] = new SpeedControllerGroup(tempL);

        for(int i = 0; i < rightMotors.length; i++){
            tempR[i] = new Talon(rightMotors[i]);
        }
        speedControllers[1] = new SpeedControllerGroup(tempR);

        if(SWAP_RIGHT_LEFT_WHEEL_SIDES){// swap if the sides are opposite
            SpeedControllerGroup temp = speedControllers[1];
            speedControllers[1] = speedControllers[0];
            speedControllers[0] = temp;
        }
        
        diffDrive = new DiffrentialDrive(speedControllers[0], speedControllers[1]);//left, then right
    }
}