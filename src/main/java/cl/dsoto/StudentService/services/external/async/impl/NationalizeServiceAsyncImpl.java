package cl.dsoto.StudentService.services.external.async.impl;

import cl.dsoto.StudentService.dto.extras.NationPrediction;
import cl.dsoto.StudentService.services.external.async.NationalizeServiceAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class NationalizeServiceAsyncImpl implements NationalizeServiceAsync {

    RestTemplate restClient = new RestTemplate();
    final String URL = "https://api.nationalize.io/?name=";

    public CompletableFuture<NationPrediction> getNationPrediction(String name) {
        String url = URL + name;
        NationPrediction response = restClient.getForObject(url, NationPrediction.class);
        return CompletableFuture.completedFuture(response);
    }

}
