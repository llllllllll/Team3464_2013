/* Team 3464 2013 robot code
 * @Authors: Joe Jevnik, Jack Pugmire, Zayd Salah, Trevor Aron, Tomas Bravo.
 * @Version: YoloBot4000
 */
package robotcode;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;


public class Team3464_2013 extends SimpleRobot {
    
    int forwardDriveTime = 1500;
    double autoSpeed = .5;
    RobotDrive drive = new RobotDrive(1,3,2,4);
    DigitalInput topSensor = new DigitalInput(1);
    DigitalInput lowSensor = new DigitalInput(2);
    Joystick leftJoy = new Joystick(1);
    Joystick rightJoy = new Joystick(2);
    Jaguar winchMotor = new Jaguar(3);
    Jaguar shoulderMotor = new Jaguar(4);
    Jaguar leftSideMotor = new Jaguar(5);
    Jaguar rightSideMotor = new Jaguar(6);
    Servo frisbeeServo = new Servo(7);
    boolean manualClimbOn = false;
    boolean dropperOpen = false;
    double joyDeadZone = 0.15;
    
    long firstTime = -1;
    long lastTime = -1;
    long maxTimeDiff = 50;
    
    long correctionTime = 50; 
    /**
     * The robot drives forward for forwardDriveTime, then spin clockwise until 
     * the robot finds the left side of the bottom goal. Once it finds the tape, 
     * the robot will drive forward until it is within a set distance from the 
     * wall and then deliver the Frisbees to the goal.
     */
    public void autonomous() {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < forwardDriveTime)
            drive.tankDrive(-autoSpeed, -autoSpeed);        
        while(topSensor.get() && isAutonomous()){
            drive.tankDrive(-.8*autoSpeed, .8*autoSpeed);
        }
        firstTime = System.currentTimeMillis();
        while(!topSensor.get()){
            drive.tankDrive(-.8*autoSpeed, .8*autoSpeed);
        }
        lastTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - lastTime < (lastTime-firstTime)){
            drive.tankDrive(.85*autoSpeed, -.85*autoSpeed);
        }
        while(lowSensor.get() && isAutonomous())
            drive.tankDrive(-autoSpeed, -autoSpeed);
        long tmp = System.currentTimeMillis();
        while(System.currentTimeMillis() - tmp <= correctionTime)
            drive.tankDrive(-0.3, 0.3);
    }

    /**
     * 
     */
    public void operatorControl() {
        frisbeeServo.set(0);
        while(isOperatorControl()) {
            drive.tankDrive(leftJoy.getMagnitude() < joyDeadZone ? 0 : leftJoy.getAxis(Joystick.AxisType.kY),
                    rightJoy.getMagnitude() < joyDeadZone ? 0 : rightJoy.getAxis(Joystick.AxisType.kY));
        }
        while(manualClimbOn && isOperatorControl())
            manualClimb();
    }
   
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    }
    
    /*
     * 
     */
    private String manualClimb(){
        winchMotor.set(leftJoy.getMagnitude() < joyDeadZone ? 0 : leftJoy.getAxis(Joystick.AxisType.kY));
        if(leftJoy.getRawButton(Keybinds.sideForward)){
            leftSideMotor.set(.4);
            rightSideMotor.set(.4);
        }
        else if(leftJoy.getRawButton(Keybinds.sideBackward)){
            leftSideMotor.set(-.4);
            rightSideMotor.set(-.4);
        }
        else{
            leftSideMotor.set(0);
            rightSideMotor.set(0);
        }
        if(leftJoy.getRawButton(Keybinds.shoulderForward))
            shoulderMotor.set(.4);
        else if(leftJoy.getRawButton(Keybinds.shoulderBackward))
            shoulderMotor.set(-.4);
        else
            shoulderMotor.set(0);
        if(leftJoy.getRawButton(Keybinds.toggle)){
            dropperOpen = !dropperOpen;
            frisbeeServo.set(dropperOpen ? 1 : 0);
        }
        return "( ?° ?? ?°)";
        
    }
   private void autoClimb(){
       //>climbing
   }
}
