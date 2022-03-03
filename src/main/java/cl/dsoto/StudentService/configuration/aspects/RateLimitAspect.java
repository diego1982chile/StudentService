package cl.dsoto.StudentService.configuration.aspects;

/**
 * Created by root on 21-02-22.
 */

import cl.dsoto.StudentService.configuration.annotations.Rateable;
import cl.dsoto.StudentService.properties.RateLimitProps;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.ConsumptionProbe;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


@Aspect
@Configuration
//@Profile("rate-limit")
@ConfigurationProperties("api.rate-limit")
public class RateLimitAspect {


    //@Qualifier("myRateBucket")
    //@Autowired
    //Bucket myRateBucket;

    Map<String, RateLimitProps> endpoints = new HashMap<>();

    Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    // maintain a reference to myGauge
    Map<String, AtomicLong> gauges = new ConcurrentHashMap<>();

    @Autowired
    MeterRegistry meterRegistry;

    @PostConstruct
    public void init() {
        for (String endpointMethod : endpoints.keySet()) {
            RateLimitProps rateLimitProps = endpoints.get(endpointMethod);
            buckets.put(endpointMethod, Bucket4j.builder()
                    .addLimit(
                            Bandwidth.simple(rateLimitProps.getRequestsPerMinute(), Duration.ofMinutes(1))
                                    .withInitialTokens(rateLimitProps.getInitialTokens()))
                    .build());

            gauges.put(endpointMethod, meterRegistry.gauge("remaining.tokens." + endpointMethod,
                                        new AtomicLong(rateLimitProps.getInitialTokens())));
        }
    }



    public Map<String, RateLimitProps> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Map<String, RateLimitProps> endpoints) {
        this.endpoints = endpoints;
    }

    @Pointcut("execution(@cl.dsoto.StudentService.configuration.annotations.Rateable * *.*(..))")
    @SuppressWarnings("unused")
    private void rateable() {
    }

    //Declares the around advice that is applied before and after the method matching with a pointcut expression
    @Before(value= "rateable()")
    public void beforeAdvice(JoinPoint jp) throws Throwable
    {
        System.out.println("The method aroundAdvice() before invokation of the method " + jp.getSignature().getName() + " method");

        Signature sig = jp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException ("Esta anotación solo se puede usar en métodos");
        }
        msig = (MethodSignature) sig;
        Object target = jp.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        if(!buckets.containsKey(currentMethod.getName())) {
            int requestsPerMinute = msig.getMethod().getAnnotation(Rateable.class).requestsPerMinute();
            int initialTokens = msig.getMethod().getAnnotation(Rateable.class).initialTokens();

            buckets.put(currentMethod.getName(), Bucket4j.builder()
                    .addLimit(
                            Bandwidth.simple(requestsPerMinute, Duration.ofMinutes(1))
                                    .withInitialTokens(initialTokens))
                    .build());
        }

        do {
            ConsumptionProbe probe = buckets.get(currentMethod.getName()).tryConsumeAndReturnRemaining(1);

            gauges.get(currentMethod.getName()).set(probe.getRemainingTokens());

            if(probe.isConsumed()) {
                return;
            }
            else {
                long timeUntilRefill = TimeUnit.NANOSECONDS.toMillis(probe.getNanosToWaitForRefill());

                try {
                    Thread.sleep(timeUntilRefill);
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        while(true);

    }


}
