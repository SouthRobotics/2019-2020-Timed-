

public class DriveTrain {
    public static boolean SWAP_RIGHT_LEFT_WHEEL_SIDES = false;//in case left and right are wrong (swapped), change this

    private double speedMultiplier;
    private DiffrentialDrive diffDrive;
    private OI input;
    private SpeedControllerGroup RSpeedCtrl;
    private SpeedControllerGroup LSpeedCtrl;
    private Talon[] motors;

    public DriveTrain(int[] leftMotors, int[] rightMotors, OI input, double speedMultiplier){
        this.LMotors = LMotors;
        this.RMotors = RMotors;
        this.input = input;
        this.speedMultiplier = speedMultiplier;
        diffDrive = new DiffrentialDrive();//todo
        Talon[] tempR = new Talon[RMotors.length];
        Talon[] tempL = new Talon[LMotors.length];

        for(int i = 0; i < RMotors.length; i++){
            tempR[i] = new Talon(RMotors[i]);
        }
        RSpeedCtrl = new SpeedControllerGroup(tempR);

        for(int i = 0; i < LMotors.length; i++){
            tempL[i] = new Talon(LMotors[i]);
        }
        LSpeedCtrl = new SpeedControllerGroup(tempL);

        if(SWAP_RIGHT_LEFT_WHEEL_SIDES){// swap if the sides are opposite
            SpeedControllerGroup temp = RSpeedCtrl;
            RSpeedCtrl = LSpeedCtrl;
            LSpeedCtrl = temp;
        }
        
        diffDrive = new DiffrentialDrive(LSpeedCtrl, RSpeedCtrl);
    }
}