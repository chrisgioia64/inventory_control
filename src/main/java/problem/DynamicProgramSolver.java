package problem;

public abstract class DynamicProgramSolver<T extends Experiment> {

    protected T experiment;

    public DynamicProgramSolver(T experiment) {
        this.experiment = experiment;
    }

}
