package problem.inventory_control;

/**
 * The final cost penalty after all N trials
 */
public interface InventoryControlFinalPenalty {

    int cost(int stockAmount);

}
