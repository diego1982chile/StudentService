package cl.dsoto.StudentService.models;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by root on 17-11-21.
 */
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rut;

    private String name;

    private LocalDate birth;

    private String gender;

    public Student() {
    }

    public Student(String rut, String name, LocalDate birth, String gender) {
        this.rut = rut;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
