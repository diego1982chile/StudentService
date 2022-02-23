package cl.dsoto.StudentService.services.external.impl.proxies;

import cl.dsoto.StudentService.dto.extras.NationPrediction;
import io.micrometer.core.annotation.Timed;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "nationalize-service", url = "${service.mock.url.nationalize:https://api.nationalize.io}")
public interface NationalizeServiceProxy {

    @GetMapping
    @Timed("nationalize_time")
    @Cacheable(value = "nationalize")
    public NationPrediction getNationPrediction(@RequestParam("name") String name);

}
