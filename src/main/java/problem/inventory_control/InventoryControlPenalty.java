package problem.inventory_control;

/**
 * The cost penalty after each time period
 */
@FunctionalInterface
public interface InventoryControlPenalty {

    public int cost(int x_k, int u_k);

}
