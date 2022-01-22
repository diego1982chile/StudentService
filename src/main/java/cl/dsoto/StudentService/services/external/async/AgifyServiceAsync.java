package cl.dsoto.StudentService.services.external.async;

import cl.dsoto.StudentService.dto.extras.AgePrediction;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface AgifyServiceAsync {

    @Async
    CompletableFuture<AgePrediction> getAgePrediction(String name);
}
