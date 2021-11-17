package cl.dsoto.StudentService.models;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by dsoto on 17-11-21.
 */
/**
 * Clase que representa a un alumno
 */
@Entity
@Table(name = "student")
public class Student {

    /**
     * Identificador o llave primaria de la entidad persistente
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Rut del alumno
     */
    private String rut;

    /**
     * Nombre del alumno
     */
    private String name;

    /**
     * Fecha de nacimiento del alumno
     */
    private LocalDate birth;

    /**
     * Género el alumno (M o F)
     */
    private String gender;

    /**
     * Constructor vacío para la deserialización del objeto
     */
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
