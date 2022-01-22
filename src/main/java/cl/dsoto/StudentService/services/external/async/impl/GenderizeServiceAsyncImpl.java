package cl.dsoto.StudentService.services.external.async.impl;

import cl.dsoto.StudentService.dto.extras.GenderPrediction;
import cl.dsoto.StudentService.services.external.async.GenderizeServiceAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class GenderizeServiceAsyncImpl implements GenderizeServiceAsync {

    RestTemplate restClient = new RestTemplate();
    final String URL = "https://api.genderize.io/?name=";

    public CompletableFuture<GenderPrediction> getGenderPrediction(String name) {
        String url = URL + name;
        GenderPrediction response = restClient.getForObject(url, GenderPrediction.class);
        return CompletableFuture.completedFuture(response);
    }

}
