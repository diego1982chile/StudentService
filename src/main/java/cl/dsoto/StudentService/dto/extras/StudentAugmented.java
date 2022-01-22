package cl.dsoto.StudentService.dto.extras;

import cl.dsoto.StudentService.models.Student;

import java.time.LocalDate;

public class StudentAugmented extends Student {

    AdditionalInfo additionalInfo;

    public StudentAugmented() {
    }

    public StudentAugmented(String rut, String name, LocalDate birth, String gender) {
        super(rut, name, birth, gender);
    }

    public StudentAugmented(String rut, String name, LocalDate birth, String gender, AdditionalInfo additionalInfo) {
        super(rut, name, birth, gender);
        this.additionalInfo = additionalInfo;
    }

    public StudentAugmented(Student stud, AgePrediction agePred, GenderPrediction genderPred, NationPrediction nationPred) {
        super(stud.getRut(), stud.getName(), stud.getBirth(), stud.getGender());
        this.additionalInfo = new AdditionalInfo(agePred, genderPred, nationPred);
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
