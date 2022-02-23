package cl.dsoto.StudentService.configuration;

/**
 * Created by root on 21-02-22.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;

import io.micrometer.core.annotation.Timed;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


@Aspect
@Configuration
//@Profile("cache")
public class CacheAspectConfig {

        private static Logger log = LoggerFactory.getLogger(CacheAspectConfig.class);
        private final Object syncLock = new Object();

        private JedisPool jedisPool;


        @PostConstruct
        public void init() {
            jedisPool = new JedisPool("127.0.0.1");
        }

        /**
         * Pointcut for all methods annotated with <code>@Cacheable</code>
         */
        @Pointcut("execution(@org.springframework.cache.annotation.Cacheable * *.*(..))")
        @SuppressWarnings("unused")
        private void cache() {
        }

        /**
         * La coincidencia de aspectos usa @Service o @Component para la clase y el método usa un @Cache personalizado
         *
         * @param jp
         * @return
         * @throws Throwable
         */
        //@Around("(@within(@annotation(org.springframework.cache.annotation.Cacheable)")
        @Around("cache()")
        private Object cacheProcess(ProceedingJoinPoint jp) throws Throwable {

            Object rval = null;

            try (Jedis jedis = jedisPool.getResource()) {
                // do operations with jedis resource
                Object[] args = jp.getArgs();
                if (args.length == 0) {
                    return jp.proceed();
                } else {
                    Signature sig = jp.getSignature();
                    MethodSignature msig = null;
                    if (!(sig instanceof MethodSignature)) {
                        throw new IllegalArgumentException ("Esta anotación solo se puede usar en métodos");
                    }
                    msig = (MethodSignature) sig;
                    Object target = jp.getTarget();
                    Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
                    //Cache cache = currentMethod.getAnnotation(Cache.class);
                    Cacheable cacheable = currentMethod.getAnnotation(Cacheable.class);
                    if (jedis == null) {
                        return jp.proceed(args);
                    } else {
                        synchronized (this.syncLock) {
                            rval = this.cacheInvoke(jedis, jp, args, 300);
                        }

                    /*
                    if (cacheable.sync()) {
                        synchronized (this.syncLock) {
                            rval = this.cacheInvoke(jedis, jp, args, 60);
                        }
                    } else {
                        rval = this.cacheInvoke(jedis, jp, args, 60);
                    }
                    */

                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return rval;

        }


        /**
         * Ejecutar lógica de caché
         *
         * @param jp
         * @param args
         * @param expire
         * @return
         * @throws Throwable
         */
        private Object cacheInvoke(Jedis jedis, ProceedingJoinPoint jp, Object[] args, int expire) throws Throwable {
            String cacheKey = (String) args[0];
            log.debug("Load from cache for key : {}", args[0]);
            //Object rval = jedis.get (cacheKey); // Datos en caché

            MethodSignature signature = (MethodSignature) jp.getSignature();

            //restore
            String json = jedis.get(cacheKey);
            Gson gson = new Gson();
            //Object rval = gson.fromJson(json,  Object.class);

            Object rval = gson.fromJson(json, signature.getReturnType());

            if (rval == null) {// No está en el caché, vaya a la base de datos para obtenerlo y colóquelo en el caché después de obtenerlo
                log.info("Miss from cache, load backend for key : {}", cacheKey);
                rval = jp.proceed (args); // ejecutar el método de destino
                if (rval != null) {
                    //jedis.set(cacheKey, (String) rval);
                    //jedis.expire(cacheKey, expire);

                    //store
                    json = gson.toJson(rval);
                    jedis.set(cacheKey,json);
                    jedis.expire(cacheKey, expire);
                }
            }

            //test
            return rval;
        }
    }
