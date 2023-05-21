package distribution;

public interface I_Distribution {
    double getProbabilityAt(int k);

    /**
     * Get the probability between a and b inclusive
     */
    double getProbabilityBetween(int a, int b);

    int sample(double r);

    int sample();

    double getMean();

    double getStandardDeviation();

}
