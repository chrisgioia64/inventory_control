package web.services;

import org.springframework.stereotype.Service;
import problem.multistage_device.*;
import web.query.CostQueryObject;

@Service
public class MultistageDeviceService {

    private MultistageDeviceDP dp;

    public MultistageDeviceService() {
        MultistageDeviceDataReader reader = new MultistageDeviceDataReader();
        MultistageDeviceData data = MultistageDeviceDataReader.readFromFile();
        dp = new MultistageDeviceDP(data, 100);
        dp.solve();
    }

    public MultistageControl[][] getDpTable() {
        return dp.getDpTable();
    }

    public MultistageDeviceData getInput() {
        return dp.getExperiment();
    }

    public MultistageDeviceDataSelection getResultsSelectedDevices(CostQueryObject query) {
        return dp.getSelection(query.getCost());
    }



}
