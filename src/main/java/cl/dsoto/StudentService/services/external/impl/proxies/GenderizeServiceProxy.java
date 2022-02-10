package cl.dsoto.StudentService.services.external.impl.proxies;

import cl.dsoto.StudentService.dto.extras.GenderPrediction;
import io.micrometer.core.annotation.Timed;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "genderize-service", url = "${GENDERIZE_URI:https://api.genderize.io}")
public interface GenderizeServiceProxy {

    @GetMapping
    @Timed("genderize_time")
    public GenderPrediction getGenderPrediction(@RequestParam("name") String name);

}
