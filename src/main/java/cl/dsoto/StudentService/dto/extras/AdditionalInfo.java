package cl.dsoto.StudentService.dto.extras;

public class AdditionalInfo {

    AgePrediction agePrediction;
    GenderPrediction genderPrediction;
    NationPrediction nationPrediction;

    public AdditionalInfo() {
    }

    public AdditionalInfo(AgePrediction agePrediction, GenderPrediction genderPrediction, NationPrediction nationPrediction) {
        this.agePrediction = agePrediction;
        this.genderPrediction = genderPrediction;
        this.nationPrediction = nationPrediction;
    }

    public AgePrediction getAgePrediction() {
        return agePrediction;
    }

    public void setAgePrediction(AgePrediction agePrediction) {
        this.agePrediction = agePrediction;
    }

    public GenderPrediction getGenderPrediction() {
        return genderPrediction;
    }

    public void setGenderPrediction(GenderPrediction genderPrediction) {
        this.genderPrediction = genderPrediction;
    }

    public NationPrediction getNationPrediction() {
        return nationPrediction;
    }

    public void setNationPrediction(NationPrediction nationPrediction) {
        this.nationPrediction = nationPrediction;
    }
}
