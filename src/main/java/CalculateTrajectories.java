public class CalculateTrajectories {
    public static Trajectory1 calculateTrajectory1(int xf, int yf, int of) {
        double cosOf = Math.cos(of);
        double sinOf = Math.sin(of);
        double a = 2 + 2 * cosOf;
        double b = 2 * yf * (1 - cosOf) + 2 * xf * sinOf;
        double c = -(Math.pow(xf, 2) + Math.pow(yf, 2));
        double calculatedRadius = (-b + Math.sqrt(Math.pow(b, 2) - 2 * a * c)) / (2 * a);
        System.out.println(calculatedRadius);
        double r = getIdealRadius(calculatedRadius);
        System.out.println(r);
        double xc2 = xf - r * sinOf;
        double yc2 = yf + r * cosOf;

        double xc1 = 0;
        double yc1 = r;

        double d = Math.sqrt(Math.pow(xc1 - xc2, 2) + Math.pow(yc1 - yc2, 2));
        double angle1 = Math.toDegrees(Math.acos(xc2 / d));

        double angle2 = of - angle1;

        System.out.println("r: " + r);
        System.out.println("a1: " + angle1);
        System.out.println("a2: " + angle2);
        System.out.println("d: " + d);

        return new Trajectory1(r, angle1, angle2, d);
     }

    public static void calculateTrajectory1Mirror(int xf, int yf, int of) {
        calculateTrajectory1(xf, Math.abs(yf), Math.abs(of));
    }

    public static void calculateTrajectory2(int xf, int yf, int of) {

    }

    public static void calculateTrajectory2Mirror(int xf, int yf, int of) {
        calculateTrajectory2(xf, Math.abs(yf), Math.abs(of));
    }

    private static double getIdealRadius(double r) {
        for (double idealRadius : Variables.idealRadius) {
            if (idealRadius <= r) return idealRadius;
        }
        return -1.0;
    }
}
