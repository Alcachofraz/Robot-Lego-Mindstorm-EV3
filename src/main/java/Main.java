class Main {
    public static void main(String[] args) {
        //Variables.getRobot().CloseEV3();
        Variables.getRobot().close();
        Variables.getStateMachine().wakeup();
        new RobotInterface().setVisible(true);
    }
}