package problem.inventory_control;

import distribution.DiscreteDistribution;
import process.BinomialDemandProcess;
import process.DemandProcess;

import java.util.LinkedList;
import java.util.List;

public class InventoryControlRunner {

    public static void main(String[] args) {
        DemandProcess process = new BinomialDemandProcess(10, 0.4);
        InventoryControlPenalty penalty = (int x) -> {
            if (x > 0) {
                return x;
            } else {
                return x * x;
            }
        };
        InventoryControlFinalPenalty finalPenalty = (int x) -> {
            return Math.abs(x) * Math.abs(x);
        };

        InventoryControlExperiment experiment = InventoryControlExperiment.builder()
                .N(50)
                .demandProcess(process)
                .maxStock(20)
                .minStock(0)
                .startStock(0)
                .penalty(penalty)
                .finalPenalty(finalPenalty)
                .build();
        InventoryControlMeanReplenisher controlLaw = new InventoryControlMeanReplenisher();
        InventoryControlExperimentRun run = new InventoryControlExperimentRun(experiment, controlLaw);

        List<Integer> events = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            run = new InventoryControlExperimentRun(experiment, controlLaw);
            run.runExperiment();
            System.out.println(run.getTotalPenalty());
            events.add((int)run.getTotalPenalty());
        }
        DiscreteDistribution dist = new DiscreteDistribution(events);
        System.out.println("Mean: " + dist.getMean());
        System.out.println("Standard Dev: " + dist.getStandardDeviation());
    }

}
