package problem.inventory_control;

public class InventoryControlExperimentRun {

    private InventoryControlExperiment experiment;
    private InventoryControlLaw control;

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

    private int[] xValues;
    private int[] wValues;
    private int[] uValues;
    private double[] penalties;
    private double finalPenalty;

    public void runExperiment() {
        xValues[0] = experiment.getStartStock();
        for (int i = 0; i < experiment.getN(); i++) {
            uValues[i] = control.orderAmount(experiment, i, xValues[i]);
            wValues[i] = experiment.getDemandProcess().sampleAt(i);
            xValues[i+1] = xValues[i] + uValues[i] - wValues[i];
            penalties[i] = experiment.getPenalty().cost(xValues[i]);
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
            System.out.println(b.toString());
        }
    }

    public double getTotalPenalty() {
        double totalPenalty = 0;
        for (int i = 0; i < experiment.getN(); i++) {
            totalPenalty += penalties[i];
        }
        return totalPenalty;
    }

}
