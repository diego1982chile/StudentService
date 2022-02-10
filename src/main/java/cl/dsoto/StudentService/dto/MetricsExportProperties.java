package cl.dsoto.StudentService.dto;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 04-02-22.
 */
@ConfigurationProperties("management.metrics.export")
public class MetricsExportProperties {

    private final Map<String, String> influx = new HashMap<>();

    public Map<String, String> getInflux() {
        return influx;
    }
}
