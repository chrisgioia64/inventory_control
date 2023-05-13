package test.problem.distribution;

import org.testng.annotations.Test;
import distribution.DiscreteDistribution;

import java.util.HashMap;
import java.util.Map;

import static org.testng.AssertJUnit.assertEquals;

public class DiscreteDistributionTest {

    @Test
    public void test1() {
        Map<Integer, Double> probMap = new HashMap<>();
        probMap.put(4, 0.4);
        probMap.put(3, 0.3);
        probMap.put(2, 0.2);
        probMap.put(5, 0.1);
        DiscreteDistribution dist = new DiscreteDistribution(probMap);
        assertEquals(0, dist.getProbabilityAt(0), 1e-2);
        assertEquals(0.2, dist.getProbabilityAt(2), 1e-2);
        assertEquals(0.3, dist.getProbabilityAt(3), 1e-2);
        assertEquals(0.4, dist.getProbabilityAt(4), 1e-2);
        assertEquals(0.1, dist.getProbabilityAt(5), 1e-2);
        assertEquals(0, dist.getProbabilityAt(6), 1e-2);
    }

    @Test
    public void test2() {
        Map<Integer, Double> probMap = new HashMap<>();
        probMap.put(4, 0.4);
        probMap.put(3, 0.3);
        probMap.put(2, 0.2);
        probMap.put(5, 0.1);
        DiscreteDistribution dist = new DiscreteDistribution(probMap);
        assertEquals(2, dist.sample(0));
        assertEquals(2, dist.sample(0.1));
        assertEquals(2, dist.sample(0.19));
        assertEquals(3, dist.sample(0.21));
        assertEquals(3, dist.sample(0.49));
        assertEquals(4, dist.sample(0.51));
        assertEquals(4, dist.sample(0.69));
        assertEquals(4, dist.sample(0.79));
        assertEquals(4, dist.sample(0.89));
        assertEquals(5, dist.sample(0.905));
        assertEquals(5, dist.sample(0.99));
        assertEquals(5, dist.sample(1.0));
    }

    @Test
    public void test3() {
        Map<Integer, Double> probMap = new HashMap<>();
        probMap.put(4, 0.4);
        probMap.put(3, 0.3);
        probMap.put(2, 0.2);
        probMap.put(5, 0.1);
        DiscreteDistribution dist = new DiscreteDistribution(probMap);
        assertEquals(0, dist.getProbabilityBetween(0, 1), 1e-2);
        assertEquals(0.2, dist.getProbabilityBetween(0, 2), 1e-2);
        assertEquals(0.5, dist.getProbabilityBetween(1, 3), 1e-2);
        assertEquals(0.9, dist.getProbabilityBetween(1, 4), 1e-2);
        assertEquals(1.0, dist.getProbabilityBetween(1, 6), 1e-2);
        assertEquals(0.0, dist.getProbabilityBetween(7, 9), 1e-2);
    }

}
