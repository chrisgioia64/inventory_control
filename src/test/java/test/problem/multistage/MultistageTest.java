package test.problem.multistage;

import org.testng.annotations.Test;
import problem.multistage_device.MultistageDeviceData;
import problem.multistage_device.MultistageDeviceDataReader;

public class MultistageTest {

    @Test
    public void test1() {
        MultistageDeviceDataReader reader = new MultistageDeviceDataReader();
        MultistageDeviceData data = MultistageDeviceDataReader.readFromFile();
        System.out.println(data);
    }

}
