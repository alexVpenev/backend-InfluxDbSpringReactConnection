package dbconnection.demo.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.util.List;

public class InfluxDBConnectionClass {

    private String token;
    private String bucket;
    private String org;

    private String url;

    public InfluxDBClient buildConnection(String url, String token, String bucket, String org) {
        setToken(token);
        setBucket(bucket);
        setOrg(org);
        setUrl(url);
        return InfluxDBClientFactory.create(getUrl(), getToken().toCharArray(), getOrg(), getBucket());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String queryData(InfluxDBClient influxDBClient) {
        String flux = "from(bucket: \"BigTrucks\")\n" +
                "  |> range(start: 2022-03-01, stop: 2022-04-20)\n" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"D222E02711T\")\n" +
                "  |> filter(fn: (r) => r[\"_field\"] == \"ECM_EEC1_Engine_Speed\")\n" +
                "  |> aggregateWindow(every: 1d, fn: mean, createEmpty: false)\n" +
                "  |> yield(name: \"mean\")";


        QueryApi queryApi = influxDBClient.getQueryApi();

        List<FluxTable> tables = queryApi.query(flux);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                return(fluxRecord.getValueByKey("_measurement").toString());
            }
        }
        return "baba ti";
    }


}
