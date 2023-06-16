package problem.multistage_device;

import lombok.Builder;
import lombok.Getter;
import problem.Experiment;

import java.util.Arrays;
import java.util.List;

@Builder
public class MultistageDeviceData implements Experiment {

    @Builder
    @Getter
    public static class SingleStageInformation {
        private int unitCost;
        private double[] probabilities;

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            b.append("Cost: " + unitCost + " ");
            b.append("Probs: ");
            b.append(Arrays.toString(probabilities));
            return b.toString();
        }
    }

    @Getter
    private List<SingleStageInformation> devices;

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (SingleStageInformation device : devices) {
            b.append(device.toString());
            b.append("\n");
        }
        return b.toString();
    }
}
