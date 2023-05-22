package distribution;

import java.util.Random;

public class BinomialDistribution implements I_Distribution {

    private final int n;
    private final double p;

    private final Random random;

    private final double[] pdfs;

    private final double[] cdfs;

    public BinomialDistribution(int n, double p) {
        this.n = n;
        this.p = p;
        this.pdfs = new double[n+1];
        this.cdfs = new double[n+1];
        this.random = new Random();
        double logProbability = n * Math.log(1 - p);
        double probFactor = Math.log(p / (1 - p));
        this.pdfs[0] = Math.exp(logProbability);
        this.cdfs[0] = pdfs[0];
        for (int i = 1; i <= n; i++) {
            double comboFactor = Math.log((n - i + 1.0) / (i + 0.0));
            logProbability += probFactor + comboFactor;
            this.pdfs[i] = Math.exp(logProbability);
            this.cdfs[i] = this.pdfs[i] + this.cdfs[i-1];
        }
    }

    public int getN() {
        return n;
    }

    @Override
    public double getProbabilityAt(int k) {
        if (k >= 0 && k <= n) {
            return pdfs[k];
        } else {
            return 0.0;
        }
    }

    @Override
    public double getProbabilityBetween(int a, int b) {
        double low = 0;
        a--;
        if (a >= 0 && a < cdfs.length) {
            low = cdfs[a];
        } else if (a >= cdfs.length) {
            low = 1;
        }
        double high = 0;
        if (b >= 0 & b < cdfs.length) {
            high = cdfs[b];
        } else if (b >= cdfs.length) {
            high = 1;
        }
        return (high - low);
    }

    @Override
    public int sample(double r) {
        return 0;
    }

    @Override
    public int sample() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (random.nextDouble() <= p) {
                count++;
            }
        }
        return count;
    }

    @Override
    public double getMean() {
        return n * p;
    }

    @Override
    public double getStandardDeviation() {
        return Math.sqrt(n * p * (1 - p));
    }
}
