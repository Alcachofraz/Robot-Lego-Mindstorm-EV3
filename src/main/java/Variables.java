public class Variables {
    static final int ROBOT_COMMUNICATION_DELAY_MS = 50;
    private static String robotName = "";
    private static boolean on = false;
    private static int radius = 0, angle = 0, distance = 0;
    //private static RobotLegoEV3 robot = new RobotLegoEV3();
    private static Robot robot = new Robot();
    private static StateMachine stateMachine = new StateMachine();

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

    static public int getRadius() {
        return radius;
    }

    static public void setRadius(int radius) {
        Variables.radius = radius;
    }

    static public int getAngle() {
        return angle;
    }

    static public void setAngle(int angle) {
        Variables.angle = angle;
    }

    static public int getDistance() {
        return distance;
    }

    static public void setDistance(int distance) {
        Variables.distance = distance;
    }

    /*static public RobotLegoEV3 getRobot() {
        return robot;
    }*/

    static public Robot getRobot() {
        return robot;
    }

    /*static public void setRobot(RobotLegoEV3 robot) {
        Variables.robot = robot;
    }*/

    static public void setRobot(Robot robot) {
        Variables.robot = robot;
    }

    public static StateMachine getStateMachine() {
        return stateMachine;
    }
}
