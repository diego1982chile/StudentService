package cl.dsoto.StudentService.services.external.async;

import cl.dsoto.StudentService.dto.extras.NationPrediction;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface NationalizeServiceAsync {

    @Async
    CompletableFuture<NationPrediction> getNationPrediction(String name);
}
