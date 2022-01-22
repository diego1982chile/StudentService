package cl.dsoto.StudentService.services.external;

import cl.dsoto.StudentService.dto.extras.GenderPrediction;

public interface GenderizeService {

    GenderPrediction getGenderPrediction(String name);
}
