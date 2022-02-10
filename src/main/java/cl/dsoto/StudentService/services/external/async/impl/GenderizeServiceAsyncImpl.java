package cl.dsoto.StudentService.services.external.async.impl;

import cl.dsoto.StudentService.configuration.AppPropsConfig;
import cl.dsoto.StudentService.dto.extras.GenderPrediction;
import cl.dsoto.StudentService.services.external.async.GenderizeServiceAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class GenderizeServiceAsyncImpl implements GenderizeServiceAsync {

    RestTemplate restClient = new RestTemplate();
    final String URL = "https://api.genderize.io/?name=[name]&apikey=[apikey]";

    @Autowired
    private AppPropsConfig appPropsConfig;

    public CompletableFuture<GenderPrediction> getGenderPrediction(String name) {
        String url = URL.replace("[name]",name).replace("[apikey]", appPropsConfig.getKey());
        GenderPrediction response = restClient.getForObject(url, GenderPrediction.class);
        return CompletableFuture.completedFuture(response);
    }

}
