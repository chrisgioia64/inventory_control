package problem;

import lombok.Getter;

public abstract class DynamicProgramSolver<T extends Experiment> {

    @Getter
    protected T experiment;

    public DynamicProgramSolver(T experiment) {
        this.experiment = experiment;
    }

}
