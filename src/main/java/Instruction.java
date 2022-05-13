public class Instruction {
    public double rotationCount;
    public double initialRotationCount;
    /**
     * It's important that each iteration invokes interpreter methods only once.
     */
    public Iteration iteration;

    public Instruction() {
        this.rotationCount = 0;
    }
}