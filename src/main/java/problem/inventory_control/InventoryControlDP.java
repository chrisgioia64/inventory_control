package problem.inventory_control;

import process.DemandProcess;

public class InventoryControlDP {

    private InventoryControlExperiment experiment;

    private Control[][] dpTable;

    private int N;

    private int numStates;

    private static class Control {
        private int u;
        private double expectedCost;

        public Control(int u, double expectedCost) {
            this.u = u;
            this.expectedCost = expectedCost;
        }

        @Override
        public String toString() {
            return u + " " + String.format("%.1f", expectedCost);
        }
    }

    public void printStates() {
        for (int i = N; i >= 0; i--) {
            System.out.println("--- " + i + " ---");
            for (int j = 0; j < numStates; j++) {
                int state = j + experiment.getMinStock();
                System.out.println(String.format("%-4d -> %s", state, dpTable[i][j]));
            }
            System.out.println();
            System.out.println();
        }
    }

    public InventoryControlDP(InventoryControlExperiment experiment) {
        this.experiment = experiment;
        this.N = experiment.getN();
        this.numStates = experiment.getMaxStock() - experiment.getMinStock() + 1;
        this.dpTable = new Control[N+1][numStates];
    }

    public void solve() {
        for (int i = 0; i < dpTable[0].length; i++) {
            int stock = experiment.getMinStock() + i;
            int finalCost = experiment.getFinalPenalty().cost(stock);
            dpTable[N][i] = new Control(0, finalCost);
        }
        InventoryControlPenalty penalty = experiment.getPenalty();
        for (int period = N-1; period >= 0; period--) {
            for (int i = 0; i < numStates; i++) {
                int x_k = experiment.getMinStock() + i;
                int low_control = 0;
                int upper_control = experiment.getMaxStock() - x_k;
                double minCostSoFar = Integer.MAX_VALUE;
                int bestControlSoFar = -1;
                for (int u = low_control; u <= upper_control; u++) {
                    DemandProcess process = experiment.getDemandProcess();
                    int low_w = process.getLowerBound(i);
                    int upper_w = process.getUpperBound(i);
                    double expected_cost = 0.0;
                    for (int w = low_w; w <= upper_w; w++) {
                        double prob = process.getProbability(w, i);
                        double cost = penalty.cost(x_k + u - w, u);
                        int nextState = Math.max(x_k, experiment.getMinStock());
                        nextState = Math.min(nextState, experiment.getMaxStock());
                        int nextStateIndex = nextState - experiment.getMinStock();
                        double nextStateCost = dpTable[period+1][nextStateIndex].expectedCost;
                        expected_cost += prob * (cost + nextStateCost);
                    }
                    if (expected_cost < minCostSoFar) {
                        bestControlSoFar = u;
                        minCostSoFar = expected_cost;
                    }
                }
                dpTable[period][i] = new Control(bestControlSoFar, minCostSoFar);
            }
        }
    }
}
