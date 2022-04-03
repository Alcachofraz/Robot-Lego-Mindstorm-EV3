import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StateMachine {
    static final int ROBOT_COMMUNICATION_DELAY_MS = 50;

    private Queue<Instruction> queue;

    private final ScheduledExecutorService stateMachineExecutor;

    /**
     * Removes head of instruction queue if it exists and
     * its iteration returns true. Otherwise, does nothing.
     * This runnable should be called continuously.
     */
    private final Runnable stateMachine = () -> {
        Instruction instruction = queue.peek();
        if (instruction != null && instruction.iterate()) {
            queue.remove();
        }
    };

    /**
     * Calling this constructor is not enough to start
     * the state machine. Once constructed, state machine
     * is asleep, and wakeup() should be called.
     */
    public StateMachine() {
        this.queue = new LinkedList<>();
        this.stateMachineExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Start state machine.
     */
    public void wakeup() {
        this.stateMachineExecutor.scheduleAtFixedRate(
                stateMachine,
                0,
                ROBOT_COMMUNICATION_DELAY_MS,
                TimeUnit.MILLISECONDS
        );
    }

    /**
     * Stop state machine.
     */
    public void sleep() {
        this.stateMachineExecutor.shutdown();
    }

    /**
     * Places instruction in instruction queue.
     * @param instruction Instruction to be placed
     *                    to the instruction queue.
     */
    public void queuePlace(Instruction instruction) {
        queue.add(instruction);
    }

    /**
     * Stops state machine, clears instruction queue,
     * adds the instruction specified as argument and
     * restarts state machine.
     * @param instruction Instruction that will start
     *                    executing immediately.
     */
    public void queueOverride(Instruction instruction) {
        sleep(); // Stop state machine, or conflicts may happen
        queue.clear();
        queue.add(instruction);
        wakeup();
    }
}

