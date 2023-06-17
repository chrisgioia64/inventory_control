package problem.multistage_device;

import problem.DynamicProgramSolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MultistageDeviceDP extends DynamicProgramSolver<MultistageDeviceData> {

    private MultistageControl[][] dpTable;
    private int totalCost;

    public MultistageDeviceDP(MultistageDeviceData experiment, int totalCost) {
        super(experiment);
        this.totalCost = totalCost;
        this.dpTable = new MultistageControl[experiment.getDevices().size()][totalCost+1];
    }

    public MultistageControl[][] getDpTable() {
        return dpTable;
    }

    public MultistageDeviceDataSelection getSelection(int totalCost) {
        LinkedList<MultistageDeviceDataSelection.DeviceSelection> devices = new LinkedList<>();
        int currentCost = totalCost;

        for (int i = dpTable.length - 1; i >= 0; i--) {
            MultistageDeviceData.SingleStageInformation singleStage = experiment.getDevices().get(i);
            MultistageDeviceDataSelection.DeviceSelection device = new MultistageDeviceDataSelection.DeviceSelection();
            device.setNumCost(singleStage.getUnitCost());
            List<MultistageDeviceDataSelection.ItemSelection> items = new LinkedList<>();
            int idx = 0;
            double[] probs = singleStage.getProbabilities();
            MultistageControl control = dpTable[i][currentCost];
            for (int j = 1; j < probs.length; j++) {
                MultistageDeviceDataSelection.ItemSelection item = new MultistageDeviceDataSelection.ItemSelection();
                item.setProbability(probs[j]);
                if (control.quantity == j) {
                    item.setSelected(true);
                }
                items.add(item);
            }
            currentCost = currentCost - (control.quantity - 1) * singleStage.getUnitCost();
            device.setItems(items);
            devices.addFirst(device);
        }
        double totalProb = Math.exp(dpTable[dpTable.length-1][totalCost].nll);
        MultistageDeviceDataSelection obj = new MultistageDeviceDataSelection(devices,
                totalProb);
        return obj;
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
                dpTable[0][j] = new MultistageControl(nll, i);
            }
        }
        int start = (device.getProbabilities().length - 1) * cost;
        int numDevices = device.getProbabilities().length - 1;
        if (start < dpTable[0].length) {
            double nll = dpTable[0][start-1].nll;
            int j = start;
            while (j <= totalCost) {
                dpTable[0][j] = new MultistageControl(nll, numDevices);
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
                dpTable[i][j] = new MultistageControl(NLLBestSoFar, soldBestSoFar);
            }
        }
    }

}
