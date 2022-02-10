package cl.dsoto.StudentService.services.external.impl.proxies;

import cl.dsoto.StudentService.dto.extras.AgePrediction;
import io.micrometer.core.annotation.Timed;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "agify-service", url = "${AGIFY_URI:https://api.agify.io}")
public interface AgifyServiceProxy {

    @GetMapping
    @Timed("agify_time")
    public AgePrediction getAgePrediction(@RequestParam("name") String name);

}
