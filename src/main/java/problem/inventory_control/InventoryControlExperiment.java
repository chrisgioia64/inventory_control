package problem.inventory_control;

import lombok.Builder;
import problem.Experiment;
import process.DemandProcess;


@Builder
public class InventoryControlExperiment implements Experiment {

    /** Number of periods. */
    private int N;

    /** The maximum stock that can be stored at any one time.
     * Maximum value of x_k */
    private int maxStock;

    /** Min value of stock.
     * Minimum value of x_k.
     * A value of 0 indicates that excess demand is lost from point to point. */
    private int minStock;

    /**
     * The starting stock (x_0)
     */
    private int startStock;

    private InventoryControlPenalty penalty;
    private InventoryControlFinalPenalty finalPenalty;
    private DemandProcess demandProcess;

    public int getN() {
        return N;
    }

    public int getMaxStock() {
        return maxStock;
    }

    public int getMinStock() {
        return minStock;
    }

    public int getStartStock() {
        return startStock;
    }

    public InventoryControlPenalty getPenalty() {
        return penalty;
    }

    public InventoryControlFinalPenalty getFinalPenalty() {
        return finalPenalty;
    }

    public DemandProcess getDemandProcess() {
        return demandProcess;
    }
}
