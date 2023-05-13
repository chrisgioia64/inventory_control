package problem.inventory_control;

public interface InventoryControlLaw {

    int orderAmount(InventoryControlExperiment experiment, int k, int x_k);

}
