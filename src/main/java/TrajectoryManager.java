public class TrajectoryManager {
    public static boolean run(int xf, int yf, int of) {
        Trajectory t = chooseTrajectory(xf, yf, of);
        if (t == null) return false;
        return t.run(Variables.getRobot());
    }

    /**
     * If xf greater than yf AND The point where rect R intersects the X
     * axis is greater than 0, Trajectory1 is chosen.
     *
     *
     *
     * Otherwise, null is returned.
     *
     * R being the rect that intersects (xf, yf) with angle of.
     */
    private static Trajectory chooseTrajectory(int xf, int yf, int of) {
        if (yf < 0) of = -of;
        if (of > 0 && xf > yf && Math.abs(Math.tan(Math.toRadians(180 - 90 - of)) * yf) <= xf) {
            System.out.println("Chose Trajectory 1");
            return CalculateTrajectories.calculateTrajectory1(
                    CalculateTrajectories.calculateTrajectory1Attributes(xf, yf, of));
        }
        else {
            TrajectoryAttributes attributes = CalculateTrajectories.calculateTrajectory2Attributes(xf, yf, of);
            if (xf > yf && of < 45 && of > -90 && attributes.yc2 < attributes.yc1) {
                System.out.println("Chose Trajectory 2");
                return CalculateTrajectories.calculateTrajectory2(attributes);
            }
            else {
                attributes = CalculateTrajectories.calculateTrajectory3Attributes(xf, yf, of);
                if (xf > yf && of < 45 && of > -90 && attributes.yc2 > attributes.yc1) {
                    System.out.println("Chose Trajectory 3");
                    return CalculateTrajectories.calculateTrajectory3(attributes);
                }
            }
        }
        return null;
    }

    public static void runWallChaser() {
        Variables.getRobot().runWallChaser();
    }
}
