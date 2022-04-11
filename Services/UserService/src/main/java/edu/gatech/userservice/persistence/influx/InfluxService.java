package edu.gatech.userservice.persistence.influx;

import com.influxdb.client.BucketsApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.Bucket;
import com.influxdb.client.domain.Organization;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import edu.gatech.userservice.models.VitalPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InfluxService {

    private final InfluxDBClient influxDBClient;
    private Organization org;

    @Autowired
    public InfluxService(@Value("${influx.url}") String url, @Value("${influx.token}") String token,
                         @Value("${influx.org}") String orgName) {
        influxDBClient = InfluxDBClientFactory.create(url, token.toCharArray(), orgName);
        for (Organization org: influxDBClient.getOrganizationsApi().findOrganizations()) {
            if (org.getName().equals(orgName)) {
                this.org = org;
            }
        }
    }

    public List<VitalPoint> getVitals(Integer patientId, Integer limit) {
        String queryString = "from(bucket:\"%s\") |> range(start:-50d) |> top(n:%s, columns:[\"_time\"])"
                .formatted(patientId.toString(), limit.toString());
        List<FluxTable> fluxTables = influxDBClient.getQueryApi().query(queryString);
        List<VitalPoint> res = new ArrayList<>();
        for (int i = 0; i < fluxTables.get(0).getRecords().size(); i++) {
            VitalPoint point = new VitalPoint();
            res.add(point);
        }
        for (FluxTable table: fluxTables) {
            int i = 0;
            for (FluxRecord record: table.getRecords()) {
                res.get(i).setTimeMillis(Objects.requireNonNull(record.getTime()).toEpochMilli());
                switch (Objects.requireNonNull(record.getField())) {
                    case "bp" -> res.get(i).setBp((Double) record.getValue());
                    case "ecg" -> res.get(i).setEcg((Double) record.getValue());
                    case "spo2" -> res.get(i).setSpo2((Double) record.getValue());
                }
                i++;
            }
        }

        return res;
    }

    public boolean writeVitalsData(List<Point> vitalDataPoints, Integer patientId) {
        BucketsApi bucketsApi = influxDBClient.getBucketsApi();
        Bucket bucket = bucketsApi.findBucketByName(patientId.toString());
        if (bucket == null) {
            bucketsApi.createBucket(patientId.toString(), org);
        }
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        writeApi.writePoints(patientId.toString(), org.getId(), vitalDataPoints);
        return true;
    }

}
