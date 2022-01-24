package cl.dsoto.StudentService.services.external.impl.proxies;

import cl.dsoto.StudentService.configuration.ApplicationProperties;
import cl.dsoto.StudentService.dto.extras.AgePrediction;
import cl.dsoto.StudentService.services.external.AgifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@FeignClient(name = "agify-service", url = "${CURRENCY_EXCHANGE_URI:https://api.agify.io}")
public interface AgifyServiceProxy {

    @GetMapping
    public AgePrediction getAgePrediction(@RequestParam("name") String name);

}
