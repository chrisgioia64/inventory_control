package problem.multistage_device;

public class MultistageDeviceRunner {

    public static void main(String[] args) {
        MultistageDeviceDataReader reader = new MultistageDeviceDataReader();
        MultistageDeviceData data = MultistageDeviceDataReader.readFromFile();

        MultistageDeviceDP dp = new MultistageDeviceDP(data, 100);
        dp.solve();
        dp.printTable();

        MultistageDeviceDataSelection selecton = dp.getSelection(10);
        System.out.println(selecton);
    }

}
