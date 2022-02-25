package cl.dsoto.StudentService.services.external.impl.proxies;

import cl.dsoto.StudentService.configuration.annotations.Rateable;
import cl.dsoto.StudentService.dto.extras.AgePrediction;
import io.micrometer.core.annotation.Timed;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@FeignClient(name = "agify-service", url = "${service.mock.url:https://api.agify.io}")
@FeignClient(name = "agify-service", url = "${service.mock.url.agify:https://api.agify.io}")
public interface AgifyServiceProxy {

    @GetMapping
    @Timed("agify_time")
    @Cacheable(value = "agify")
    @Rateable(requestsPerMinute = 10, initialTokens = 10)
    public AgePrediction getAgePrediction(@RequestParam("name") String name);

}
