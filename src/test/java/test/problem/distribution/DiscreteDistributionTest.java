package test.problem.distribution;

import org.testng.annotations.Test;
import distribution.DiscreteDistribution;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

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

    @Test
    public void test4() {
        List<Integer> events = new LinkedList<>();
        events.add(3);
        events.add(4);
        events.add(7);
        events.add(3);
        events.add(7);
        Map<Integer, Double> probMap = DiscreteDistribution.getProbMap(events);
        assertEquals(3, probMap.size());
        assertFalse(probMap.containsKey(0));
        assertFalse(probMap.containsKey(1));
        assertTrue(probMap.containsKey(3));
        assertTrue(probMap.containsKey(4));
        assertTrue(probMap.containsKey(7));
        assertEquals(0.4, probMap.get(3), 1e-3);
        assertEquals(0.2, probMap.get(4), 1e-3);
        assertEquals(0.4, probMap.get(7), 1e-3);
    }

    @Test
    public void test5() {
        List<Integer> events = new LinkedList<>();
        events.add(1);
        events.add(3);
        events.add(4);
        events.add(4);
        events.add(5);
        events.add(5);
        events.add(6);
        events.add(6);
        events.add(7);
        events.add(9);
        DiscreteDistribution dist = new DiscreteDistribution(events);
        assertEquals(2.1, dist.getStandardDeviation(), 1e-2);
    }

}
