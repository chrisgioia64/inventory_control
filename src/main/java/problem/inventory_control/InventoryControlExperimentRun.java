package problem.inventory_control;

public class InventoryControlExperimentRun {

    private final InventoryControlExperiment experiment;
    private final InventoryControlLaw control;

    public InventoryControlExperimentRun(InventoryControlExperiment experiment,
                                         InventoryControlLaw control) {
        this.experiment = experiment;
        this.control = control;
        int N = experiment.getN();
        this.xValues = new int[N+1];
        this.wValues = new int[N+1];
        this.uValues = new int[N+1];
        this.penalties = new double[N+1];
    }

    private final int[] xValues;
    private final int[] wValues;
    private final int[] uValues;
    private final double[] penalties;
    private double finalPenalty;

    public void runExperiment() {
        xValues[0] = experiment.getStartStock();
        int nextValue = -1;
        for (int i = 0; i < experiment.getN(); i++) {
            uValues[i] = control.orderAmount(experiment, i, xValues[i]);
            wValues[i] = experiment.getDemandProcess().sampleAt(i);
            nextValue = xValues[i] + uValues[i] - wValues[i];
            xValues[i+1] = Math.max(experiment.getMinStock(), nextValue);
            penalties[i] = experiment.getPenalty().cost(nextValue, uValues[i]);
        }
        finalPenalty = experiment.getFinalPenalty().cost(xValues[experiment.getN()]);
    }

    public void printResults() {
        System.out.println("i    x    u    w");
        for (int i = 0; i < experiment.getN(); i++) {
            StringBuilder b = new StringBuilder();
            b.append(String.format("%-5d", i));
            b.append(String.format("%-5d", xValues[i]));
            b.append(String.format("%-5d", uValues[i]));
            b.append(String.format("%-5d", wValues[i]));
            b.append(String.format("%-2.3f", penalties[i]));
            System.out.println(b.toString());
        }
        System.out.println("Final penalty: " + finalPenalty);
    }

    public double getTotalPenalty() {
        double totalPenalty = 0;
        for (int i = 0; i < experiment.getN(); i++) {
            totalPenalty += penalties[i];
        }
        return totalPenalty;
    }

}
