package problem.multistage_device;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

public class MultistageDeviceDataSelection {

    @Getter
    @Setter
    public static class ItemSelection {
        private double probability;
        private boolean isSelected;
    }

    @Getter
    @Setter
    public static class DeviceSelection {
        private int numCost;
        private List<ItemSelection> items;

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            b.append(numCost + "\n");
            for (ItemSelection item : items) {
                b.append(item.probability + " ");
                if (item.isSelected) {
                    b.append("(selected) ");
                }
            }
            b.append("\n");
            return b.toString();
        }
    }

    public MultistageDeviceDataSelection(List<DeviceSelection> devices, double totalProb) {
        this.devices = devices;
        this.totalProbability = totalProb;
    }

    @Getter
    private List<DeviceSelection> devices;

    @Getter
    private double totalProbability;

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (DeviceSelection device : devices) {
            b.append(device.toString());
        }
        return b.toString();
    }

}
