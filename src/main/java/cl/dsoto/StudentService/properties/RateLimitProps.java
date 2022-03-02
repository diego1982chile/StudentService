package cl.dsoto.StudentService.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by root on 21-02-22.
 */
@Data
public class RateLimitProps {

    private long requestsPerMinute;
    private long initialTokens;

    public long getRequestsPerMinute() {
        return requestsPerMinute;
    }

    public void setRequestsPerMinute(long requestsPerMinute) {
        this.requestsPerMinute = requestsPerMinute;
    }

    public long getInitialTokens() {
        return initialTokens;
    }

    public void setInitialTokens(long initialTokens) {
        this.initialTokens = initialTokens;
    }
}
