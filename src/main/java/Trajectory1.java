public class Trajectory1 implements Trajectory {
    double radius;
    double angle1;
    double angle2;
    double distance;
    boolean turnRight;

    public Trajectory1(double radius, double angle1, double angle2, double distance, boolean turnRight) {
        this.radius = radius;
        this.angle1 = angle1;
        this.angle2 = angle2;
        this.distance = distance;
        this.turnRight = turnRight;
    }

    public boolean run(Robot robot) {
        if (turnRight) {
            System.out.println("Turn Right: " + angle1 + " degrees and a radius of " + radius + " cm");
            robot.turnRight(radius, angle1);
        }
        else {
            System.out.println("Turn Left: " + angle1 + " degrees and a radius of " + radius + " cm");
            robot.turnLeft(radius, angle1);
        }
        System.out.println("Forward: " + distance + " cm");
        if (distance != 0) robot.forward(distance);
        if (turnRight) {
            System.out.println("Turn Right: " + angle2 + " degrees and a radius of " + radius + " cm");
            robot.turnRight(radius, angle1);
        }
        else {
            System.out.println("Turn Left: " + angle2 + " degrees and a radius of " + radius + " cm");
            robot.turnLeft(radius, angle2);
        }
        System.out.println("Stop");
        robot.stop(false);
        return true;
    }
}
