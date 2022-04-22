import java.util.ArrayList;
import java.util.Collections;

public class Variables {
    static final int ROBOT_COMMUNICATION_DELAY_MS = 20;
    static final double WHEEL_RADIUS = 2.8;
    static final double WHEEL_DISTANCE = 9.6;
    static final int WHEEL_RIGHT = 4;
    static final int WHEEL_LEFT = 2;
    static final int WHEELS_BOTH = 6;
    static final int DEFAULT_SPEED = 50;
    static final int MIN_SPEED = 15;
    static final int MAX_SPEED = 85;

    static final boolean usePrediction = true;
    private static String robotName = "";
    private static boolean on = false;
    private static double radius = 0, angle = 0, distance = 0;
    private static Robot robot = new Robot();
    private static final StateMachine stateMachine = new StateMachine();

    public static ArrayList<Double> idealRadius = new ArrayList<>();

    static public void init() {
        Variables.getRobot().close();
        Variables.calculateRadius();
        Variables.getStateMachine().wakeup();
    }

    static public void calculateRadius() {
        for (int vd = DEFAULT_SPEED - 1, ve = DEFAULT_SPEED + 1; vd > MIN_SPEED && ve < MAX_SPEED; vd--, ve++) {
            double f = (double)ve / (double)vd;
            idealRadius.add((WHEEL_DISTANCE / 2) * ((f + 1)/ (f - 1)));
        }
        //Collections.reverse(idealRadius);
    }

    static public String getRobotName() {
        return robotName;
    }

    static public void setRobotName(String robotName) {
        Variables.robotName = robotName;
    }

    static public boolean isOn() {
        return on;
    }

    static public void setOn(boolean on) {
        Variables.on = on;
    }

    static public double getRadius() {
        return radius;
    }

    static public void setRadius(double radius) {
        Variables.radius = radius;
    }

    static public double getAngle() {
        return angle;
    }

    static public void setAngle(double angle) {
        Variables.angle = angle;
    }

    static public double getDistance() {
        return distance;
    }

    static public void setDistance(double distance) {
        Variables.distance = distance;
    }

    static public Robot getRobot() {
        return robot;
    }

    static public void setRobot(Robot robot) {
        Variables.robot = robot;
    }

    public static StateMachine getStateMachine() {
        return stateMachine;
    }
}
