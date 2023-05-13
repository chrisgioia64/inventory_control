package test.problem.distribution;

import distribution.BinomialDistribution;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BinomialDistributionTest {

    @Test
    public void test1() {
        BinomialDistribution dist = new BinomialDistribution(6, 0.4);
        assertEquals(0.0, dist.getProbabilityAt(-1), 1e-3);
        assertEquals(0.046656, dist.getProbabilityAt(0), 1e-3);
        assertEquals(0.186624, dist.getProbabilityAt(1), 1e-3);
        assertEquals(0.31104, dist.getProbabilityAt(2), 1e-3);
        assertEquals(0.27648, dist.getProbabilityAt(3), 1e-3);
        assertEquals(0.13824, dist.getProbabilityAt(4), 1e-3);
        assertEquals(0.036864, dist.getProbabilityAt(5), 1e-3);
        assertEquals(0.004096, dist.getProbabilityAt(6), 1e-3);
    }

    @Test
    public void test2() {
        BinomialDistribution dist = new BinomialDistribution(6, 0.4);
        assertEquals(0.0, dist.getProbabilityBetween(-2, -1), 1e-3);
        assertEquals(0.046656, dist.getProbabilityBetween(0, 0), 1e-3);
        assertEquals(0.23328, dist.getProbabilityBetween(0, 1), 1e-3);
        assertEquals(0.54432, dist.getProbabilityBetween(0, 2), 1e-3);
        assertEquals(0.497664, dist.getProbabilityBetween(1, 2), 1e-3);
        assertEquals(0.451584, dist.getProbabilityBetween(3, 5), 1e-3);
        assertEquals(0.45568, dist.getProbabilityBetween(3, 6), 1e-3);
        assertEquals(0.45568, dist.getProbabilityBetween(3, 7), 1e-3);
    }


}
