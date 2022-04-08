import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class StateMachine {

    private Queue<Instruction> queue;

    private final ScheduledExecutorService stateMachineExecutor;
    private ScheduledFuture<?> stateMachineHandle;

    /**
     * Removes head of instruction queue if it exists and
     * its iteration returns true. Otherwise, does nothing.
     * This runnable should be called continuously.
     */
    private final Runnable stateMachine = () -> {
        Instruction instruction = queue.peek();
        if (instruction != null && instruction.iteration.iterate()) {
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
        stateMachineHandle = this.stateMachineExecutor.scheduleAtFixedRate(
                stateMachine,
                0,
                Variables.ROBOT_COMMUNICATION_DELAY_MS,
                TimeUnit.MILLISECONDS
        );
    }

    /**
     * Stop state machine.
     */
    public void sleep() {
        this.stateMachineHandle.cancel(false);
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

