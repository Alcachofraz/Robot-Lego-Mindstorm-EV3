class Main {
    public static void main(String[] args) {
        Variables.init();
        new RobotInterface().setVisible(true);
        /*System.out.println(Variables.idealRadius);
        Variables.getRobot().open("EVC");
        Trajectory1 t = CalculateTrajectories.calculateTrajectory1(100, 10, 70);

        Variables.getRobot().turnLeft(t.radius, t.angle1);
        Variables.getRobot().forward(t.distance);
        Variables.getRobot().turnLeft(t.radius, t.angle2);
        Variables.getRobot().stop(false);*/
    }
}