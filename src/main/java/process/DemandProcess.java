package process;

public interface DemandProcess {

    int getLowerBound(int k);

    int getUpperBound(int k);

    /**
     * Returns the probability of getting demand r at time period k
     */
    double getProbability(int k, int r);

    /**
     * Returns the mean for time period k
     */
    double getMean(int k);

    /**
     * Sample a value for time period k
     */
    int sampleAt(int k);

}
