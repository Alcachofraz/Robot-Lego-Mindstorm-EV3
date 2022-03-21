class Main {
    public static void main(String[] args) {
        Variables.getRobot().CloseEV3();
        new RobotInterface().setVisible(true);
    }
}