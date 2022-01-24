package cl.dsoto.StudentService.services.external.async.impl;

import cl.dsoto.StudentService.configuration.ApplicationProperties;
import cl.dsoto.StudentService.dto.extras.AgePrediction;
import cl.dsoto.StudentService.services.external.async.AgifyServiceAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class AgifyServiceAsyncImpl implements AgifyServiceAsync {

    RestTemplate restClient = new RestTemplate();
    final String URL = "https://api.agify.io/?name=[name]&apikey=[apikey]";

    @Autowired
    private ApplicationProperties applicationProperties;

    public CompletableFuture<AgePrediction> getAgePrediction(String name) {
        String url = URL.replace("[name]",name).replace("[apikey]", applicationProperties.getKey());
        AgePrediction response = restClient.getForObject(url, AgePrediction.class);
        return CompletableFuture.completedFuture(response);
    }

}
