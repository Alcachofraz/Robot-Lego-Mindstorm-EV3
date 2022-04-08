public class Robot {
    final double WHEEL_RADIUS = 2.8;
    final double WHEEL_DISTANCE = 9.5;
    final int WHEEL_RIGHT = 4;
    final int WHEEL_LEFT = 2;
    final int WHEELS_BOTH = 6;
    final int DEFAULT_SPEED = 50;
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

    private double averageRotationCount() {
        int[] rotations = interpreter.RotationCount(WHEEL_RIGHT, WHEEL_LEFT);
        return Math.abs((rotations[0] + rotations[1]) / 2.0);
    }

    /**
     * Robot moves forward [distanceCm].
     * @param distanceCm distance do move in cm.
     */
    public void forward(double distanceCm) {
        double rads = Math.abs(distanceCm) / WHEEL_RADIUS;
        double degrees = (rads * 180) / Math.PI;
        int speed = distanceCm < 0 ? -DEFAULT_SPEED : DEFAULT_SPEED;

        Instruction instructionPrepare = new Instruction();
        instructionPrepare.iteration = () -> {
            interpreter.ResetAll();
            interpreter.OnFwd(WHEEL_RIGHT, speed, WHEEL_LEFT, speed);
            return true;
        };
        Instruction instructionIteration = new Instruction();
        instructionIteration.iteration = () -> {
            double rotation = averageRotationCount();
            double delta = rotation - instructionIteration.rotationCount;
            instructionIteration.rotationCount = (int)Math.round(rotation);
            return rotation + delta >= degrees;
        };
        Variables.getStateMachine().queuePlace(
                instructionPrepare
        );
        Variables.getStateMachine().queuePlace(
                instructionIteration
        );
        stop(false);
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
        double factor = (radiusCm + (WHEEL_DISTANCE / 2)) / (radiusCm - (WHEEL_DISTANCE / 2));

        int speedRight = (int) Math.round(DEFAULT_SPEED * 2 - (DEFAULT_SPEED * 2 * factor / (1 + factor)));
        int speedLeft = (int) Math.round(DEFAULT_SPEED * 2 * factor / (1 + factor));

        double angleRads = (Math.abs(angleDegrees) * Math.PI) / 180;
        double distanceCm = angleRads * radiusCm;
        double rads = distanceCm / WHEEL_RADIUS;
        double degrees = (rads * 180) / Math.PI;

        Instruction instructionPrepare = new Instruction();
        instructionPrepare.iteration = () -> {
            interpreter.ResetAll();
            interpreter.OnFwd(WHEEL_RIGHT, angleDegrees < 0 ? -speedRight : speedRight, WHEEL_LEFT, angleDegrees < 0 ? -speedLeft : speedLeft);
            return true;
        };
        Instruction instructionIteration = new Instruction();
        instructionIteration.iteration = () -> {
            double rotation = averageRotationCount();
            double delta = rotation - instructionIteration.rotationCount;
            instructionIteration.rotationCount = (int)Math.round(rotation);
            return rotation + delta >= degrees;
        };

        Variables.getStateMachine().queuePlace(
                instructionPrepare
        );
        Variables.getStateMachine().queuePlace(
                instructionIteration
        );
        stop(false);
    }

    /**
     * Turn left, with [radiusCm] and [angleDegrees].
     * @param radiusCm radius of the turn in cm.
     * @param angleDegrees angle of the turn in degrees.
     */
    public void turnLeft(double radiusCm, double angleDegrees) {
        double factor = (radiusCm + (WHEEL_DISTANCE / 2)) / (radiusCm - (WHEEL_DISTANCE / 2));

        int speedLeft = (int) Math.round(DEFAULT_SPEED * 2 - (DEFAULT_SPEED * 2 * factor / (1 + factor)));
        int speedRight = (int) Math.round(DEFAULT_SPEED * 2 * factor / (1 + factor));

        double angleRads = (Math.abs(angleDegrees) * Math.PI) / 180;
        double distanceCm = angleRads * radiusCm;
        double rads = distanceCm / WHEEL_RADIUS;
        double degrees = (rads * 180) / Math.PI;

        Instruction instructionPrepare = new Instruction();
        instructionPrepare.iteration = () -> {
            interpreter.OnFwd(WHEEL_RIGHT, angleDegrees < 0 ? -speedRight : speedRight, WHEEL_LEFT, angleDegrees < 0 ? -speedLeft : speedLeft);
            return true;
        };
        Instruction instructionIteration = new Instruction();
        double initialRotationCount = averageRotationCount();
        instructionIteration.rotationCount = initialRotationCount;
        instructionIteration.iteration = () -> {
            double rotation = averageRotationCount();
            //System.out.println(rotation);
            double delta = rotation - instructionIteration.rotationCount;
            instructionIteration.rotationCount = rotation;
            return rotation + delta - initialRotationCount > degrees;
        };

        Variables.getStateMachine().queuePlace(
                instructionPrepare
        );
        Variables.getStateMachine().queuePlace(
                instructionIteration
        );
        stop(false);
    }

    /**
     * Robot stops.
     * @param async If true, stops asynchronously (overriding all other
     *              instructions in queue). Otherwise, waits for all other instructions in queue.
     */
    public void stop(boolean async) {
        Instruction instruction = new Instruction();
        instruction.iteration = () -> {
            interpreter.Off(WHEELS_BOTH);
            return true;
        };
        if (async) Variables.getStateMachine().queueOverride(instruction);
        else Variables.getStateMachine().queuePlace(instruction);
    }
}
