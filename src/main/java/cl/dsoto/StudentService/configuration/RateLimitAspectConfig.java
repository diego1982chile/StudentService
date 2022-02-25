package cl.dsoto.StudentService.configuration;

/**
 * Created by root on 21-02-22.
 */

import cl.dsoto.StudentService.configuration.annotations.Rateable;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.ConsumptionProbe;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


@Aspect
@Configuration
//@Profile("rate-limit")
public class RateLimitAspectConfig {


    //@Qualifier("myRateBucket")
    //@Autowired
    //Bucket myRateBucket;

    Map<String, Bucket> buckets = new ConcurrentHashMap<>();

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

        int requestsPerMinute = currentMethod.getAnnotation(Rateable.class).requestsPerMinute();
        int initialTokens = currentMethod.getAnnotation(Rateable.class).initialTokens();

        if(!buckets.containsKey(currentMethod.getName())) {
            buckets.put(currentMethod.getName(), Bucket4j.builder()
                    .addLimit(
                            Bandwidth.simple(requestsPerMinute, Duration.ofMinutes(1))
                                    .withInitialTokens(initialTokens))
                    .build());
        }

        ConsumptionProbe probe = buckets.get(currentMethod.getName()).tryConsumeAndReturnRemaining(1);

        if(!probe.isConsumed()) {
            long timeUntilRefill = TimeUnit.NANOSECONDS.toMillis(probe.getNanosToWaitForRefill());

            try {
                Thread.sleep(timeUntilRefill);
            }
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
