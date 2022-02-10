package cl.dsoto.StudentService.configuration;

/**
 * Created by root on 09-02-22.
 */

import cl.dsoto.StudentService.dto.extras.StudentAugmented;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

@Aspect
@Configuration
@Profile("metrics")
public class MetricAspectConfig {

    @Autowired
    MeterRegistry meterRegistry;

    //Displays all the available methods i.e. the advice will be called for all the methods
    @Pointcut(value= "execution(* cl.dsoto.StudentService.services.implementations.StudentServiceImpl.getStudentsAugmentedPaginated(..))")
    private void getStudentsAugmentedPaginated()
    {
    }

    //Declares the around advice that is applied before and after the method matching with a pointcut expression
    @Around(value= "getStudentsAugmentedPaginated()")
    public Object aroundAdvice(ProceedingJoinPoint jp) throws Throwable
    {
        System.out.println("The method aroundAdvice() before invokation of the method " + jp.getSignature().getName() + " method");
        Object result = null;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try
        {
            result = jp.proceed();
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(result != null) {
                Page<StudentAugmented> page = (Page<StudentAugmented>) result;
            }
            stopWatch.stop();

            meterRegistry.counter("student_count").increment();
            // check time
            meterRegistry.timer("student_time").record(stopWatch.getLastTaskTimeMillis(), TimeUnit.MILLISECONDS);
        }

        return null;
    }

}
