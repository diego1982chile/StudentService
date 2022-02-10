package cl.dsoto.StudentService.services.external.impl;

import cl.dsoto.StudentService.configuration.AppPropsConfig;
import cl.dsoto.StudentService.dto.extras.GenderPrediction;
import cl.dsoto.StudentService.services.external.GenderizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Primary
public class GenderizeServiceImpl implements GenderizeService {

    RestTemplate restClient = new RestTemplate();
    final String URL = "https://api.genderize.io/?name=[name]&apikey=[apikey]";

    @Autowired
    private AppPropsConfig appPropsConfig;

    public GenderPrediction getGenderPrediction(String name) {
        String url = URL.replace("[name]",name).replace("[apikey]", appPropsConfig.getKey());
        GenderPrediction response = restClient.getForObject(url, GenderPrediction.class);
        return response;
    }

}
