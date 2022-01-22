package cl.dsoto.StudentService.services.external.impl;

import cl.dsoto.StudentService.dto.extras.GenderPrediction;
import cl.dsoto.StudentService.services.external.GenderizeService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GenderizeServiceImpl implements GenderizeService {

    RestTemplate restClient = new RestTemplate();
    final String URL = "https://api.genderize.io/?name=";

    public GenderPrediction getGenderPrediction(String name) {
        String url = URL + name;
        GenderPrediction response = restClient.getForObject(url, GenderPrediction.class);
        return response;
    }

}
