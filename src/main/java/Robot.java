public class Robot {
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
        int[] rotations = interpreter.RotationCount(Variables.WHEEL_RIGHT, Variables.WHEEL_LEFT);
        return Math.abs((rotations[0] + rotations[1]) / 2.0);
    }

    /**
     * Robot moves forward [distanceCm].
     * @param distanceCm distance do move in cm.
     */
    public void forward(double distanceCm) {
        double rads = Math.abs(distanceCm) / Variables.WHEEL_RADIUS;
        double degrees = (rads * 180) / Math.PI;
        int speed = distanceCm < 0 ? -Variables.DEFAULT_SPEED : Variables.DEFAULT_SPEED;

        Instruction instructionIteration = new Instruction();

        Instruction instructionPrepare = new Instruction();
        instructionPrepare.iteration = () -> {
            double temp = averageRotationCount();
            instructionIteration.rotationCount = 0;
            instructionIteration.initialRotationCount = temp;
            interpreter.OnFwd(Variables.WHEEL_RIGHT, speed, Variables.WHEEL_LEFT, speed);
            return true;
        };
        instructionIteration.iteration = () -> {
            double rotation = Math.abs(averageRotationCount() - instructionIteration.initialRotationCount);
            double delta = Variables.usePrediction ? rotation - instructionIteration.rotationCount : 0;
            instructionIteration.rotationCount = rotation;
            return rotation + delta > degrees;
        };
        Variables.getStateMachine().queuePlace(
                instructionPrepare
        );
        Variables.getStateMachine().queuePlace(
                instructionIteration
        );
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
        double factor = (radiusCm + (Variables.WHEEL_DISTANCE / 2)) / (radiusCm - (Variables.WHEEL_DISTANCE / 2));

        int tempSpeedRight = (int) Math.round(Variables.DEFAULT_SPEED * 2 - (Variables.DEFAULT_SPEED * 2 * factor / (1 + factor)));
        int tempSpeedLeft = (int) Math.round(Variables.DEFAULT_SPEED * 2 * factor / (1 + factor));

        tempSpeedLeft = Math.min(tempSpeedLeft, Variables.MAX_SPEED);
        tempSpeedRight = Math.min(tempSpeedRight, Variables.MAX_SPEED);
        tempSpeedLeft = Math.max(tempSpeedLeft, Variables.MIN_SPEED);
        tempSpeedRight = Math.max(tempSpeedRight, Variables.MIN_SPEED);

        int speedLeft = tempSpeedLeft;
        int speedRight = tempSpeedRight;

        double angleRads = (Math.abs(angleDegrees) * Math.PI) / 180;
        double distanceCm = angleRads * radiusCm;
        double rads = distanceCm / Variables.WHEEL_RADIUS;
        double degrees = (rads * 180) / Math.PI;

        Instruction instructionIteration = new Instruction();
        Instruction instructionPrepare = new Instruction();
        instructionPrepare.iteration = () -> {
            double temp = averageRotationCount();
            instructionIteration.rotationCount = 0;
            instructionIteration.initialRotationCount = temp;
            interpreter.OnFwd(Variables.WHEEL_RIGHT, angleDegrees < 0 ? -speedRight : speedRight, Variables.WHEEL_LEFT, angleDegrees < 0 ? -speedLeft : speedLeft);
            return true;
        };
        instructionIteration.iteration = () -> {
            double rotation = Math.abs(averageRotationCount() - instructionIteration.initialRotationCount);
            double delta = Variables.usePrediction ? rotation - instructionIteration.rotationCount : 0;
            instructionIteration.rotationCount = rotation;
            return rotation + delta > degrees;
        };

        Variables.getStateMachine().queuePlace(
                instructionPrepare
        );
        Variables.getStateMachine().queuePlace(
                instructionIteration
        );
    }

    /**
     * Turn left, with [radiusCm] and [angleDegrees].
     * @param radiusCm radius of the turn in cm.
     * @param angleDegrees angle of the turn in degrees.
     */
    public void turnLeft(double radiusCm, double angleDegrees) {
        double factor = (radiusCm + (Variables.WHEEL_DISTANCE / 2)) / (radiusCm - (Variables.WHEEL_DISTANCE / 2));

        int tempSpeedLeft = (int) Math.round(Variables.DEFAULT_SPEED * 2 - (Variables.DEFAULT_SPEED * 2 * factor / (1 + factor)));
        int tempSpeedRight = (int) Math.round(Variables.DEFAULT_SPEED * 2 * factor / (1 + factor));

        tempSpeedLeft = Math.min(tempSpeedLeft, Variables.MAX_SPEED);
        tempSpeedRight = Math.min(tempSpeedRight, Variables.MAX_SPEED);
        tempSpeedLeft = Math.max(tempSpeedLeft, Variables.MIN_SPEED);
        tempSpeedRight = Math.max(tempSpeedRight, Variables.MIN_SPEED);

        int speedLeft = tempSpeedLeft;
        int speedRight = tempSpeedRight;

        double angleRads = (Math.abs(angleDegrees) * Math.PI) / 180;
        double distanceCm = angleRads * radiusCm;
        double rads = distanceCm / Variables.WHEEL_RADIUS;
        double degrees = (rads * 180) / Math.PI;

        Instruction instructionIteration = new Instruction();
        Instruction instructionPrepare = new Instruction();
        instructionPrepare.iteration = () -> {
            double temp = averageRotationCount();
            instructionIteration.rotationCount = 0;
            instructionIteration.initialRotationCount = temp;
            interpreter.OnFwd(Variables.WHEEL_RIGHT, angleDegrees < 0 ? -speedRight : speedRight, Variables.WHEEL_LEFT, angleDegrees < 0 ? -speedLeft : speedLeft);
            return true;
        };
        instructionIteration.iteration = () -> {
            double rotation = Math.abs(averageRotationCount() - instructionIteration.initialRotationCount);
            double delta = Variables.usePrediction ? rotation - instructionIteration.rotationCount : 0;
            instructionIteration.rotationCount = rotation;
            return rotation + delta > degrees;
        };

        Variables.getStateMachine().queuePlace(
                instructionPrepare
        );
        Variables.getStateMachine().queuePlace(
                instructionIteration
        );
    }

    /**
     * Robot stops.
     * @param async If true, stops asynchronously (overriding all other
     *              instructions in queue). Otherwise, waits for all other instructions in queue.
     */
    public void stop(boolean async) {
        Instruction instruction = new Instruction();
        instruction.iteration = () -> {
            interpreter.Off(Variables.WHEELS_BOTH);
            return true;
        };
        if (async) Variables.getStateMachine().queueOverride(instruction);
        else Variables.getStateMachine().queuePlace(instruction);
    }
}
