public class CalculateTrajectories {
    public static TrajectoryAttributes calculateTrajectory1Attributes(int xf, int yf, int of) {
        int yfabs = Math.abs(yf);
        double ofrad = Math.toRadians(of);
        double cosOf = Math.cos(ofrad);
        double sinOf = Math.sin(ofrad);
        double a = 2 + 2 * cosOf;
        double b = 2 * yfabs * (1 - cosOf) + 2 * xf * sinOf;
        double c = -(Math.pow(xf, 2) + Math.pow(yfabs, 2));

        double calculatedRadius = 0;
        if (Math.abs(a) >= 0.1) calculatedRadius = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
        else calculatedRadius = -c/b;
        System.out.println("Calculated Radius:" +  calculatedRadius);

        double r = getIdealRadius(calculatedRadius);
        System.out.println("Optimized Radius:" + r);

        return new TrajectoryAttributes(r, yfabs + r * cosOf, 0, xf - r * sinOf, r, xf, yf, of);
    }

    public static TrajectoryAttributes calculateTrajectory2Attributes(int xf, int yf, int of) {
        double ofrad = Math.toRadians(of);
        int yfabs = Math.abs(yf);
        double cosOf = Math.cos(ofrad);
        double sinOf = Math.sin(ofrad);

        // r1 = r2
        double calculatedRadius = 0;
        double a = 2 - 2 * cosOf;
        double b = 2 * yfabs * (1 + cosOf) - 2 * xf * sinOf;
        double c = -(Math.pow(xf, 2) + Math.pow(yfabs, 2));
        if (Math.abs(a) >= 0.1) calculatedRadius = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
        else calculatedRadius = -c/b;
        System.out.println("Calculated Radius:" +  calculatedRadius);

        double r = getIdealRadius(calculatedRadius);
        System.out.println("Optimized Radius:" + r);

        return new TrajectoryAttributes(r, yfabs - r*cosOf, 0, xf + r * sinOf, r, xf, yf, of);
    }

    public static TrajectoryAttributes calculateTrajectory3Attributes(int xf, int yf, int of) {
        double ofrad = Math.toRadians(Math.abs(of));
        int yfabs = Math.abs(yf);
        double cosOf = Math.cos(ofrad);
        double sinOf = Math.sin(ofrad);

        // r1 = r2
        double calculatedRadius = 0;
        double a = 2 - 2 * cosOf;
        double b = 2 * yfabs * (1 + cosOf) + 2 * xf * sinOf;
        double c = -(Math.pow(xf, 2) + Math.pow(yfabs, 2));

        if(Math.abs(a) >= 0.1) calculatedRadius = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
        else calculatedRadius = -c / b;
        System.out.println("Calculated Radius:" +  calculatedRadius);

        double r = getIdealRadius(calculatedRadius);
        System.out.println("Optimized Radius:" + r);

        return new TrajectoryAttributes(r, yfabs - r * cosOf, 0, xf + r * sinOf, r, xf, yf, of);
    }

    public static Trajectory1 calculateTrajectory1(TrajectoryAttributes attributes) {
        double d = Math.sqrt(Math.pow(attributes.xc1 - attributes.xc2, 2) + Math.pow(attributes.yc1 - attributes.yc2, 2));
        double angle1 = Math.toDegrees(Math.acos(attributes.xc2 / d));

        double angle2 = attributes.of - angle1;

        return new Trajectory1(attributes.r, angle1, angle2, d, attributes.yf < 0);
    }

    public static Trajectory2 calculateTrajectory2(TrajectoryAttributes attributes) {

        double d12 = Math.sqrt(Math.pow(attributes.xc1 - attributes.xc2, 2) + Math.pow(attributes.yc1 - attributes.yc2, 2));

        double alpha = Math.acos(attributes.r/(d12/2));
        double d = d12 * Math.sin(alpha);
        double angle1 = Math.toDegrees(Math.asin(attributes.xc2/d12) - alpha);
        double angle2 = angle1 - attributes.of;

        return new Trajectory2(attributes.r, angle1, angle2, d, attributes.yf < 0);
    }

    public static Trajectory3 calculateTrajectory3(TrajectoryAttributes attributes) {
        double d12 = Math.sqrt(Math.pow(attributes.xc1 - attributes.xc2, 2) + Math.pow(attributes.yc1 - attributes.yc2, 2));

        double alpha = Math.acos(attributes.r/(d12/2));
        double beta = Math.asin(attributes.xc2/d12);
        double d = d12 * Math.sin(alpha);
        double angle1 = 180 - Math.toDegrees(alpha+beta);
        double angle2 = angle1 - attributes.of;

        return new Trajectory3(attributes.r, angle1, angle2, d, attributes.yf < 0);
    }

    private static double getIdealRadius(double r) {
        for (double idealRadius : Variables.idealRadius) {
            if (idealRadius <= r) return idealRadius;
        }
        return -1.0;
    }
}
