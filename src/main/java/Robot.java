import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Robot {
    final double WHEEL_RADIUS = 2.8;
    final double WHEEL_DISTANCE = 9.0;
    final int WHEEL_RIGHT = 4;
    final int WHEEL_LEFT = 2;
    final int WHEELS_BOTH = 6;
    final int DEFAULT_SPEED = 50;
    final int ROBOT_COMMUNICATION_DELAY = 50;

    private final ArrayList<ScheduledExecutorService> executors = new ArrayList<>();

    private final InterpretadorEV3 interpreter;

    public Robot() {
        interpreter = new InterpretadorEV3();
    }

    public boolean open(String robotName) {
        return interpreter.OpenEV3(robotName);
    }

    public void close() {
        interpreter.CloseEV3();
    }

    /**
     * Shut down all executors.
     */
    public void clearExecutors() {
        for (ScheduledExecutorService executor : executors) {
            executor.shutdown();
        }
    }

    /**
     * Robot moves forward [distanceCm].
     * @param distanceCm distance do move in cm.
     */
    public void forward(double distanceCm) {
        clearExecutors();
        interpreter.ResetAll();
        double rads = Math.abs(distanceCm) / WHEEL_RADIUS;
        double degrees = (rads * 180) / Math.PI;
        int speed = distanceCm < 0 ? -DEFAULT_SPEED : DEFAULT_SPEED;
        interpreter.OnFwd(WHEEL_RIGHT, speed, WHEEL_LEFT, speed);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (interpreter.RotationCount(WHEEL_RIGHT) <= -degrees || interpreter.RotationCount(WHEEL_RIGHT) >= degrees) {
                stop();
                executor.shutdown();
            }
        }, 0, ROBOT_COMMUNICATION_DELAY, TimeUnit.MILLISECONDS);
        executors.add(executor);
    }

    /**
     * Activate turbo. If going straight both wheels
     * achieve a speed of 100. If in a turn, the outer
     * wheel achieves a speed of 100.
     */
    public void turbo() {

    }

    /**
     * Turn right, with [radiusCm] and [angleDegrees].
     * @param radiusCm radius of the turn in cm.
     * @param angleDegrees angle of the turn in degrees.
     */
    public void turnRight(double radiusCm, double angleDegrees) {
        clearExecutors();
        interpreter.ResetAll();
        double factor = (radiusCm + (WHEEL_DISTANCE / 2)) / (radiusCm - (WHEEL_DISTANCE / 2));
        double angleRads = (angleDegrees * Math.PI) / 180;
        double distanceCm = angleRads * (radiusCm + (WHEEL_DISTANCE / 2));
        double rads = distanceCm / WHEEL_RADIUS;
        double degrees = (rads * 180) / Math.PI;
        interpreter.OnFwd(WHEEL_RIGHT, (int) Math.round(DEFAULT_SPEED / factor), WHEEL_LEFT, DEFAULT_SPEED);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (interpreter.RotationCount(WHEEL_LEFT) >= degrees) {
                stop();
                executor.shutdown();
            }
        }, 0, ROBOT_COMMUNICATION_DELAY, TimeUnit.MILLISECONDS);
        executors.add(executor);
    }

    /**
     * Turn left, with [radiusCm] and [angleDegrees].
     * @param radiusCm radius of the turn in cm.
     * @param angleDegrees angle of the turn in degrees.
     */
    public void turnLeft(double radiusCm, double angleDegrees) {
        clearExecutors();
        interpreter.ResetAll();
        double factor = (radiusCm + (WHEEL_DISTANCE / 2)) / (radiusCm - (WHEEL_DISTANCE / 2));
        double angleRads = (angleDegrees * Math.PI) / 180;
        double distanceCm = angleRads * (radiusCm + (WHEEL_DISTANCE / 2));
        double rads = distanceCm / WHEEL_RADIUS;
        double degrees = (rads * 180) / Math.PI;
        interpreter.OnFwd(WHEEL_RIGHT, DEFAULT_SPEED, WHEEL_LEFT, (int) Math.round(DEFAULT_SPEED / factor));
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (interpreter.RotationCount(WHEEL_RIGHT) >= degrees) {
                stop();
                executor.shutdown();
            }
        }, 0, ROBOT_COMMUNICATION_DELAY, TimeUnit.MILLISECONDS);
        executors.add(executor);
    }

    /**
     * Robot stops immediately, ignoring all queued
     * instructions.
     */
    public void stopNow() {
        clearExecutors();
        interpreter.Off(WHEELS_BOTH);
    }

    /**
     * Robot stops once every other instruction in queue
     * has been executed.
     */
    public void stop() {
        interpreter.Off(WHEELS_BOTH);
    }
}
