package cl.dsoto.StudentService.services.external.impl.proxies;

import cl.dsoto.StudentService.configuration.ApplicationProperties;
import cl.dsoto.StudentService.dto.extras.GenderPrediction;
import cl.dsoto.StudentService.services.external.GenderizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@FeignClient(name = "genderize-service", url = "${https://api.genderize.io}")
public interface GenderizeServiceProxy {

    @GetMapping
    public GenderPrediction getGenderPrediction(@RequestParam("name") String name);

}
