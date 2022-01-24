package cl.dsoto.StudentService.services.external.impl.proxies;

import cl.dsoto.StudentService.configuration.ApplicationProperties;
import cl.dsoto.StudentService.dto.extras.GenderPrediction;
import cl.dsoto.StudentService.dto.extras.NationPrediction;
import cl.dsoto.StudentService.services.external.NationalizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@FeignClient(name = "nationalize-service", url = "${https://api.nationalize.io}")
public interface NationalizeServiceProxy {

    @GetMapping
    public NationPrediction getNationPrediction(@RequestParam("name") String name);

}
