/* Team 3464 2013 robot code
 * @Authors: Joe Jevnik, Jack Pugmire, Zayd Salah, Trevor Aron, Thomas Bravo.
 * @Version: YoloBot1000
 */
package robotcode;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;

public class Team3464_2013 extends SimpleRobot {
    
    int forwardDriveTime = 1500;
    
    
    /**
     * The robot drives forward for forwardDriveTime, then spin clockwise until 
     * the robot finds the left side of the bottom goal. Once it finds the tape, 
     * the robot will drive forward until it is within a set distance from the 
     * wall and then deliver the Frisbees to the goal.
     */
    public void autonomous() {
        DigitalInput topSensor = new DigitalInput(5);
        DigitalInput lowSensor = new DigitalInput(6);
        RobotDrive drive = new RobotDrive(1,2,3,4);
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < forwardDriveTime)
            drive.tankDrive(-.4, -.4);
        while(topSensor.get() && isOperatorControl())
            drive.tankDrive(.4, -.4);
        while(lowSensor.get() && isOperatorControl())
            drive.tankDrive(-.4, -.4);
        
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        
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