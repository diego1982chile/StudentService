package cl.dsoto.StudentService.services.external;

import cl.dsoto.StudentService.dto.extras.NationPrediction;

public interface NationalizeService {

    NationPrediction getNationPrediction(String name);
}
