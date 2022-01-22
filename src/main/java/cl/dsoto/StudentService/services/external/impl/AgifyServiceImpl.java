package cl.dsoto.StudentService.services.external.impl;

import cl.dsoto.StudentService.dto.extras.AgePrediction;
import cl.dsoto.StudentService.services.external.AgifyService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AgifyServiceImpl implements AgifyService {

    RestTemplate restClient = new RestTemplate();
    final String URL = "https://api.agify.io/?name=";

    public AgePrediction getAgePrediction(String name) {
        String url = URL + name;
        AgePrediction response = restClient.getForObject(url, AgePrediction.class);
        return response;
    }

}
