package problem.inventory_control;

public class DemandDistributionCache {

    private DemandDistributionSampler demandDistribution;
    private int N;
    private Integer[] values;

    public DemandDistributionCache(int N, DemandDistributionSampler distribution) {
        this.demandDistribution = distribution;
        this.N = N;
        this.values = new Integer[N+1];
    }

    public int sampleAt(int k) {
        if (k >= 0 && k <= N) {
            if (values[k] == null) {
                values[k] = demandDistribution.sampleAt(k);
            }
            return values[k];
        } else {
            throw new IllegalArgumentException("cannot get the demand for period " + k);
        }
    }

}
