public class Trajectory2 implements Trajectory {
    double radius;
    double angle1;
    double angle2;
    double distance;
    boolean isMirror;

    public Trajectory2(double radius, double angle1, double angle2, double distance, boolean isMirror) {
        this.radius = radius;
        this.angle1 = angle1;
        this.angle2 = angle2;
        this.distance = distance;
        this.isMirror = isMirror;
    }

    public boolean run(Robot robot) {
        if (isMirror) {
            System.out.println("Turn Right: " + angle1 + " degrees and a radius of " + radius + " cm");
            robot.turnRight(radius, angle1);
        }
        else {
            System.out.println("Turn Left: " + angle1 + " degrees and a radius of " + radius + " cm");
            robot.turnLeft(radius, angle1);
        }
        System.out.println("Forward: " + distance + " cm");
        if (distance != 0) robot.forward(distance);
        if (isMirror) {
            System.out.println("Turn Left: " + angle2 + " degrees and a radius of " + radius + " cm");
            robot.turnLeft(radius, angle2);
        }
        else {
            System.out.println("Turn Right: " + angle2 + " degrees and a radius of " + radius + " cm");
            robot.turnRight(radius, angle2);
        }
        System.out.println("Stop");
        robot.stop(false);
        return true;
    }
}
