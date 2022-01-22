package cl.dsoto.StudentService.services.external;

import cl.dsoto.StudentService.dto.extras.AgePrediction;

public interface AgifyService {

    AgePrediction getAgePrediction(String name);
}
