package problem.inventory_control;

/**
 * The cost penalty after each time period
 */
@FunctionalInterface
public interface InventoryControlPenalty {

    int cost(int x_k, int u_k);

}
