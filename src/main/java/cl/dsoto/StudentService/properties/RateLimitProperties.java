package cl.dsoto.StudentService.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by root on 21-02-22.
 */
@Data
@ConfigurationProperties("api.rate-limit")
public class RateLimitProperties {

    private long myRequestsPerMinute;
    private long myInitialTokens;

    public long getMyRequestsPerMinute() {
        return myRequestsPerMinute;
    }

    public long getMyInitialTokens() {
        return myInitialTokens;
    }

    public void setMyRequestsPerMinute(long myRequestsPerMinute) {
        this.myRequestsPerMinute = myRequestsPerMinute;
    }

    public void setMyInitialTokens(long myInitialTokens) {
        this.myInitialTokens = myInitialTokens;
    }
}
