package cl.dsoto.StudentService.dto.extras;

import java.util.List;

public class NationPrediction {

    String name;
    List<Country> country;

    public NationPrediction() {
    }

    public NationPrediction(String name, List<Country> country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }
}
