/* Team 3464 2013 robot code
 * @Authors: Joe Jevnik, Jack Pugmire, Zayd Salah, Trevor Aron, Tomas Bravo.
 * @Version: YoloBot3000
 */
package robotcode;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;

public class Team3464_2013 extends SimpleRobot {
    
    int forwardDriveTime = 1500;
    double autoSpeed = .5;
    RobotDrive drive = new RobotDrive(1,3,2,4);
    DigitalInput topSensor = new DigitalInput(1);
    DigitalInput lowSensor = new DigitalInput(2);
    
    long topTime = -1;
    long maxTimeDiff = 75;
    
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
        while(isAutonomous()) {
            //If the topTime is -1, it hasn't been set yet.
            if(!topSensor.get() && topTime == -1)
                topTime = System.currentTimeMillis();
            //This might fix our corner case problem. We need the actual robot to test it, though.
            //if(!lowSensor.get()) topTime = -1;
            //If the time since top sensor was set is greater than max diff time, we need to check the low sensor.
            if((System.currentTimeMillis() - topTime >  maxTimeDiff) && topTime != -1) {
                if(!lowSensor.get()) topTime = -1;
                else break;
            }
            
            drive.tankDrive(-4*autoSpeed/5, 4*autoSpeed/5);
        }
        while(lowSensor.get() && isAutonomous())
            drive.tankDrive(-autoSpeed, -autoSpeed);
        
    }

    /**
     * 
     */
    public void operatorControl() {
        /*while(lowSensor.get() && isOperatorControl())
            drive.tankDrive(-.4,-.4);
        */
//        DigitalInput topSensor = new DigitalInput(5);
//        DigitalInput lowSensor = new DigitalInput(6);
//        RobotDrive drive = new RobotDrive(1,2,3,4);
//        while(topSensor.get() && isOperatorControl())
//            drive.tankDrive(.4, -.4);
//        long start = System.currentTimeMillis();
//        while(lowSensor.get() && isOperatorControl() && (System.currentTimeMillis() - start <= 30000))
//            drive.tankDrive(-.4, -.4);
    }
   
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
}
