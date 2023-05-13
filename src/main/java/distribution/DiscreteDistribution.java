package distribution;

import java.util.Map;
import java.util.Random;

public class DiscreteDistribution implements I_Distribution {

    private int lowerBound;
    private int upperBound;

    /** Unnormalized probabilities. */
    private double[] pdfs;
    private double[] cdfs;

    private double normalizationConstant;

    private Random random;

    private double mean;

    public DiscreteDistribution(Map<Integer, Double> probMap) {
        this.lowerBound = Integer.MAX_VALUE;
        this.upperBound = Integer.MIN_VALUE;
        this.normalizationConstant = 0;
        for (Map.Entry<Integer, Double> entry : probMap.entrySet()) {
            lowerBound = Math.min(lowerBound, entry.getKey());
            upperBound = Math.max(upperBound, entry.getKey());
            this.normalizationConstant += entry.getValue();
        }
        this.pdfs = new double[upperBound - lowerBound + 1];
        this.cdfs = new double[upperBound - lowerBound + 1];
        this.mean = 0;
        for (Map.Entry<Integer, Double> entry : probMap.entrySet()) {
            int k = entry.getKey();
            int index = k - lowerBound;
            pdfs[index] = entry.getValue();
            if (index > 0) {
                cdfs[index] = cdfs[index-1] + pdfs[index];
            } else {
                cdfs[index] = pdfs[index];
            }
            mean += k * pdfs[index];
        }
        mean = mean / normalizationConstant;
    }

    @Override
    public double getProbabilityAt(int k) {
        int index = k - lowerBound;
        if (index >= 0 && index < pdfs.length) {
            return pdfs[index] / normalizationConstant;
        } else {
            return 0;
        }
    }

    @Override
    public double getProbabilityBetween(int a, int b) {
        int index1 = a - lowerBound - 1;
        int index2 = b - lowerBound;
        double low = 0;
        if (index1 >= 0 && index1 < cdfs.length) {
            low = cdfs[index1];
        } else if (index1 >= cdfs.length) {
            low = 1;
        }
        double high = 0;
        if (index2 >= 0 & index2 < cdfs.length) {
            high = cdfs[index2];
        } else if (index2 >= cdfs.length) {
            high = 1;
        }
        return (high - low) * normalizationConstant;
    }

    @Override
    public int sample() {
        double r = random.nextDouble();
        return sample(r);
    }

    @Override
    public double getMean() {
        return mean;
    }

    @Override
    public int sample(double r) {
        double current = 0;
        for (int index = 0; index < pdfs.length; index++) {
            double nextValue = (current + (pdfs[index] / normalizationConstant));
            if (r <= nextValue) {
                return index + lowerBound;
            }
            current = nextValue;
        }
        return upperBound;
    }

}
