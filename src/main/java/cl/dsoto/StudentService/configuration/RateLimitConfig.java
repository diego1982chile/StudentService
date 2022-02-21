package cl.dsoto.StudentService.configuration;

import cl.dsoto.StudentService.properties.RateLimitProperties;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Created by root on 21-02-22.
 */
@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
public class RateLimitConfig {

    @Bean
    public Bucket myRateBucket(RateLimitProperties props) {

        return Bucket4j.builder()
                .addLimit(
                        Bandwidth.simple(props.getMyRequestsPerMinute(), Duration.ofMinutes(1))
                                .withInitialTokens(props.getMyInitialTokens()))
                .build();
    }

}
