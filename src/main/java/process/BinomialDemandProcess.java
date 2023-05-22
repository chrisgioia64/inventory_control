package process;

import distribution.BinomialDistribution;
import process.DemandProcess;

public class BinomialDemandProcess implements DemandProcess {

    private final BinomialDistribution distribution;

    public BinomialDemandProcess(int n, double p) {
        this.distribution = new BinomialDistribution(n, p);
    }

    @Override
    public int getLowerBound(int k) {
        return 0;
    }

    @Override
    public int getUpperBound(int k) {
        return distribution.getN();
    }

    @Override
    public double getProbability(int k, int r) {
        return distribution.getProbabilityAt(k);
    }

    @Override
    public double getMean(int k) {
        return distribution.getMean();
    }

    @Override
    public int sampleAt(int k) {
        return distribution.sample();
    }
}
