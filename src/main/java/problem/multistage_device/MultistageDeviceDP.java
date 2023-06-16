package problem.multistage_device;

import problem.DynamicProgramSolver;

public class MultistageDeviceDP extends DynamicProgramSolver<MultistageDeviceData> {

    private class Control {
        private double nll;
        private int quantity;

        public Control(double nll, int quantity) {
            this.nll = nll;
            this.quantity = quantity;
        }
        @Override
        public String toString() {
            return nll + " : " + quantity;
        }
    }

    private Control[][] dpTable;
    private int totalCost;

    public MultistageDeviceDP(MultistageDeviceData experiment, int totalCost) {
        super(experiment);
        this.totalCost = totalCost;
        this.dpTable = new Control[experiment.getDevices().size()][totalCost+1];
    }

    public void printTable() {
        for (int i = 0; i < dpTable.length; i++) {
            System.out.println("i " + i);
            for (int j = 0; j < dpTable[0].length; j++) {
                System.out.println("cost " + j + " -> " + Math.exp(dpTable[i][j].nll) + " " +
                        dpTable[i][j].quantity);
            }
            System.out.println("----------");
        }
    }

    public void solve() {
        MultistageDeviceData.SingleStageInformation device = experiment.getDevices().get(0);
        int cost = device.getUnitCost();
        int cumulativeCost = 0;
        for (int i = 1; i < device.getProbabilities().length; i++) {
            double nll = Math.log(device.getProbabilities()[i]);
            for (int j = (i - 1) * cost; j <= Math.min(i * cost, totalCost); j++) {
                dpTable[0][j] = new Control(nll, 0);
            }
        }
        int start = (device.getProbabilities().length - 1) * cost;
        if (start < dpTable[0].length) {
            double nll = dpTable[0][start-1].nll;
            int j = start;
            while (j <= totalCost) {
                dpTable[0][j] = new Control(nll, 0);
                j++;
            }
        }


        for (int i = 1; i < experiment.getDevices().size(); i++) {
            device = experiment.getDevices().get(i);
            for (int j = 0; j <= totalCost; j++) {
                double[] probs = device.getProbabilities();
                double NLLBestSoFar = Double.NEGATIVE_INFINITY;
                int soldBestSoFar = -1;
                for (int k = 1; k < probs.length; k++) {
                    cost = device.getUnitCost() * (k - 1);
                    double currentNLL = Math.log(probs[k]);
                    if (j - cost >= 0) {
                        double pastNLL = dpTable[i-1][j-cost].nll;
                        currentNLL += pastNLL;
                        if (currentNLL > NLLBestSoFar) {
                            NLLBestSoFar = currentNLL;
                            soldBestSoFar = k;
                        }
                    } else {
                        break;
                    }
                }
                dpTable[i][j] = new Control(NLLBestSoFar, soldBestSoFar);
            }
        }
    }

}
