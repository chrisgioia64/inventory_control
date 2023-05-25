package problem.inventory_control;

import distribution.DiscreteDistribution;
import process.BinomialDemandProcess;
import process.DemandProcess;

import java.util.LinkedList;
import java.util.List;

public class InventoryControlRunner {

    public static void main(String[] args) {
        DemandProcess process = new BinomialDemandProcess(10, 0.4);
        InventoryControlPenalty penalty = (int x_k, int u_k) -> {
            int cost = u_k;
            if (x_k > 0) {
                return cost + x_k;
            } else {
                return cost + x_k * x_k;
            }
        };
        InventoryControlFinalPenalty finalPenalty = (int x) -> Math.abs(x) * Math.abs(x);

        InventoryControlExperiment experiment = InventoryControlExperiment.builder()
                .N(5)
                .demandProcess(process)
                .maxStock(10)
                .minStock(0)
                .startStock(0)
                .penalty(penalty)
                .finalPenalty(finalPenalty)
                .build();
        InventoryControlMeanReplenisher controlLaw = new InventoryControlMeanReplenisher();

        List<Integer> events = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            InventoryControlExperimentRun run = new InventoryControlExperimentRun(experiment, controlLaw);
            run.runExperiment();
            run.printResults();
            System.out.println(run.getTotalPenalty());
            events.add((int)run.getTotalPenalty());
        }
        DiscreteDistribution dist = new DiscreteDistribution(events);
        System.out.println("Mean: " + dist.getMean());
        System.out.println("Standard Dev: " + dist.getStandardDeviation());

        InventoryControlDP dp = new InventoryControlDP(experiment);
        dp.solve();
        dp.printStates();
    }

}
