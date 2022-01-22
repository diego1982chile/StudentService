package cl.dsoto.StudentService.dto.extras;

public class GenderPrediction {

    String name;
    String gender;
    double probability;

    public GenderPrediction() {
    }

    public GenderPrediction(String name, String gender, double probability) {
        this.name = name;
        this.gender = gender;
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
