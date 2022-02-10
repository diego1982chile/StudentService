package cl.dsoto.StudentService.services.external.impl;

import cl.dsoto.StudentService.configuration.AppPropsConfig;
import cl.dsoto.StudentService.dto.extras.NationPrediction;
import cl.dsoto.StudentService.services.external.NationalizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Primary
public class NationalizeServiceImpl implements NationalizeService {

    RestTemplate restClient = new RestTemplate();
    final String URL = "https://api.nationalize.io/?name=[name]&apikey=[apikey]";

    @Autowired
    private AppPropsConfig appPropsConfig;

    public NationPrediction getNationPrediction(String name) {
        String url = URL.replace("[name]",name).replace("[apikey]", appPropsConfig.getKey());
        NationPrediction response = restClient.getForObject(url, NationPrediction.class);
        return response;
    }

}
