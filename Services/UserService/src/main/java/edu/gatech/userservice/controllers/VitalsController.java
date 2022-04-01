package edu.gatech.userservice.controllers;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import edu.gatech.userservice.models.VitalPoint;
import edu.gatech.userservice.models.VitalsRegistrationRequest;
import edu.gatech.userservice.persistence.influx.InfluxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vitals")
@Slf4j
public class VitalsController {

    private final InfluxService influxService;

    @Autowired
    public VitalsController(InfluxService influxService) {
        this.influxService = influxService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerVitals(@RequestBody VitalsRegistrationRequest vitalsRegistrationRequest) {
        List<Point> dataPoints = vitalsRegistrationRequest
                .getBatchedVitals()
                .stream()
                .map(vitalPoint -> Point.measurement(vitalsRegistrationRequest.getPatientId().toString())
                        .addField("spo2", vitalPoint.getSpo2())
                        .addField("bp", vitalPoint.getBp())
                        .addField("ecg", vitalPoint.getEcg())
                        .time(vitalPoint.getTimeMillis(), WritePrecision.MS)).toList();

        if (this.influxService.writeVitalsData(dataPoints, vitalsRegistrationRequest.getPatientId())) {
            log.info("Data written");
            return ResponseEntity.accepted().body(null);
        }
        return ResponseEntity.badRequest().body(null);

    }

    @GetMapping("/retrieve")
    public ResponseEntity<List<VitalPoint>> getVitals(@RequestParam("user") Integer userId,
                                                      @RequestParam("limit") Integer limit) {
        List<VitalPoint> vitalPoints = this.influxService.getVitals(userId, limit);
        return ResponseEntity.ok(vitalPoints);
    }
}
