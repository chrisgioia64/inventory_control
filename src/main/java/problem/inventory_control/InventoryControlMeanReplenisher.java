package problem.inventory_control;

import process.DemandProcess;

public class InventoryControlMeanReplenisher implements InventoryControlLaw {

    @Override
    public int orderAmount(InventoryControlExperiment experiment, int k, int x_k) {
        DemandProcess demandProcess = experiment.getDemandProcess();
        double averageDemand = demandProcess.getMean(k);
        int orderAmount = (int) (Math.max(averageDemand - x_k, 0));
        return orderAmount;
    }

}
