package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.query.CostQueryObject;
import web.services.MultistageDeviceService;

import java.util.List;

@CrossOrigin
@RestController
public class MyController {

    @Autowired
    private MultistageDeviceService service;

    @GetMapping("/multistage/results")
    public ResponseEntity getMultistageDeviceResults() {
        return ResponseEntity.ok(service.getDpTable());
    }

    @GetMapping("/multistage/input")
    public ResponseEntity getInputData() {
        return ResponseEntity.ok(service.getInput());
    }

    @PostMapping("/multistage/results-selected")
    public ResponseEntity getResultsSelected(@RequestBody CostQueryObject obj) {
        return ResponseEntity.ok(service.getResultsSelectedDevices(obj));
    }

}
