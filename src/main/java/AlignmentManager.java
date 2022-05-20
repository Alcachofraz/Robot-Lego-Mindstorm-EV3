public class AlignmentManager {
    int distance1;
    double initialRotationCount = 0;
    int xf;
    int yf;
    int of;

    public AlignmentManager() {
        this.distance1 = 255;
    }

    public double getInitialRotationCount() {
        return initialRotationCount;
    }

    public void setInitialRotationCount(double initialRotationCount) {
        this.initialRotationCount = initialRotationCount;
    }

    public boolean setDistance(int distance) {
        if (Math.abs(distance1 - distance) > Variables.MINIMUM_DIFFERENCE_TO_ALIGN) {
            // Calculate of, yf and xf
            return true;
        }
        else {
            distance1 = distance;
            return false;
        }
    }
}
