package cl.dsoto.StudentService.services.external.impl;

import cl.dsoto.StudentService.configuration.ApplicationProperties;
import cl.dsoto.StudentService.dto.extras.AgePrediction;
import cl.dsoto.StudentService.services.external.AgifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Primary
public class AgifyServiceImpl implements AgifyService {

    RestTemplate restClient = new RestTemplate();
    final String URL = "https://api.agify.io/?name=[name]&apikey=[apikey]";

    @Autowired
    private ApplicationProperties applicationProperties;

    public AgePrediction getAgePrediction(String name) {
        String url = URL.replace("[name]",name).replace("[apikey]", applicationProperties.getKey());
        AgePrediction response = restClient.getForObject(url, AgePrediction.class);
        return response;
    }

}
