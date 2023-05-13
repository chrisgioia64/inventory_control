package problem.inventory_control;

import process.BinomialDemandProcess;
import process.DemandProcess;

public class InventoryControlRunner {

    public static void main(String[] args) {
        DemandProcess process = new BinomialDemandProcess(10, 0.4);
        InventoryControlPenalty penalty = (int x) -> {
            return Math.abs(x) * Math.abs(x);
        };
        InventoryControlFinalPenalty finalPenalty = (int x) -> {
            return Math.abs(x) * Math.abs(x);
        };

        InventoryControlExperiment experiment = InventoryControlExperiment.builder()
                .N(10)
                .demandProcess(process)
                .maxStock(20)
                .minStock(0)
                .startStock(0)
                .penalty(penalty)
                .finalPenalty(finalPenalty)
                .build();
        InventoryControlMeanReplenisher controlLaw = new InventoryControlMeanReplenisher();
        InventoryControlExperimentRun run = new InventoryControlExperimentRun(experiment, controlLaw);

        for (int i = 0; i < 10; i++) {
            run = new InventoryControlExperimentRun(experiment, controlLaw);
            run.runExperiment();
            run.printResults();
            System.out.println(run.getTotalPenalty());
        }
    }

}
