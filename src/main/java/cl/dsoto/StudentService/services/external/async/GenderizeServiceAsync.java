package cl.dsoto.StudentService.services.external.async;

import cl.dsoto.StudentService.dto.extras.GenderPrediction;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface GenderizeServiceAsync {

    @Async
    CompletableFuture<GenderPrediction> getGenderPrediction(String name);
}
