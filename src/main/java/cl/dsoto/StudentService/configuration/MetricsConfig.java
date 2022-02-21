package cl.dsoto.StudentService.configuration;

import cl.dsoto.StudentService.dto.MetricsExportProperties;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxMeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Duration;

/**
 * Created by root on 04-02-22.
 */
@Configuration
@EnableConfigurationProperties(MetricsExportProperties.class)
@Profile(value = "metrics")
public class MetricsConfig {


    @Bean
    public InfluxConfigAdapter influxConfig(MetricsExportProperties props) {
        return new InfluxConfigAdapter(props);
    }

    @Bean
    public InfluxMeterRegistry influxMeterRegistry(InfluxConfigAdapter influxConfig) {
        return new InfluxMeterRegistry(influxConfig, Clock.SYSTEM);
    }


    @Bean
    public TimedAspect timedAspect(MeterRegistry meterRegistry) {
        return new TimedAspect(meterRegistry);
    }

    @RequiredArgsConstructor
    public static class InfluxConfigAdapter implements InfluxConfig {


        private MetricsExportProperties props = null;

        public InfluxConfigAdapter(MetricsExportProperties props) {
            this.props = props;
        }

        @Override
        public String get(String key) {

            String truncatedKey = key.replaceFirst("influx\\.", "");

            return props.getInflux().get(truncatedKey);
        }

    }
}
