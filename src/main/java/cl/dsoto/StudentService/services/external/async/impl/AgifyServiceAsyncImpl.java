package cl.dsoto.StudentService.services.external.async.impl;

import cl.dsoto.StudentService.dto.extras.AgePrediction;
import cl.dsoto.StudentService.services.external.async.AgifyServiceAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class AgifyServiceAsyncImpl implements AgifyServiceAsync {

    RestTemplate restClient = new RestTemplate();
    final String URL = "https://api.agify.io/?name=";

    public CompletableFuture<AgePrediction> getAgePrediction(String name) {
        String url = URL + name;
        AgePrediction response = restClient.getForObject(url, AgePrediction.class);
        return CompletableFuture.completedFuture(response);
    }

}
