package cl.dsoto.StudentService.services.external.impl;

import cl.dsoto.StudentService.dto.extras.NationPrediction;
import cl.dsoto.StudentService.services.external.NationalizeService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NationalizeServiceImpl implements NationalizeService {

    RestTemplate restClient = new RestTemplate();
    final String URL = "https://api.nationalize.io/?name=";

    public NationPrediction getNationPrediction(String name) {
        String url = URL + name;
        NationPrediction response = restClient.getForObject(url, NationPrediction.class);
        return response;
    }

}
