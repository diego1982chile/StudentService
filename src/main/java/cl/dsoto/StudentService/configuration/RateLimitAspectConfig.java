package cl.dsoto.StudentService.configuration;

/**
 * Created by root on 21-02-22.
 */

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;


@Aspect
@Configuration
@Profile("rate-limit")
public class RateLimitAspectConfig {


    //@Qualifier("myRateBucket")
    @Autowired
    Bucket myRateBucket;

    //Displays all the available methods i.e. the advice will be called for all the methods
    @Pointcut(value= "execution(* cl.dsoto.StudentService.services.implementations.StudentServiceImpl.getStudentsAugmentedPaginated(..))")
    private void getStudentsAugmentedPaginated()
    {
    }

    //Declares the around advice that is applied before and after the method matching with a pointcut expression
    @Before(value= "getStudentsAugmentedPaginated()")
    public void beforeAdvice(JoinPoint jp) throws Throwable
    {
        System.out.println("The method aroundAdvice() before invokation of the method " + jp.getSignature().getName() + " method");

        ConsumptionProbe probe = myRateBucket.tryConsumeAndReturnRemaining(1);

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
