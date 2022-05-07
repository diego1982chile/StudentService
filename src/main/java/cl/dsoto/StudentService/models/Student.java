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
    @Column(unique = true)
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
     * Género del alumno (M o F)
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

    public Student(Long id, String rut, String name, LocalDate birth, String gender) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        // Si ambos objetos están persistidos comparar sus ids
        if(id != null && student.id != null) {
            return id.equals(student.id);
        }
        else {
            // Si alguno de los objetos no está persistido, comparar el resto de sus campos
            if (!rut.equals(student.rut)) return false;
            if (!name.equals(student.name)) return false;
            if (!birth.equals(student.birth)) return false;
            return gender.equals(student.gender);
        }
    }

    @Override
    public int hashCode() {
        int result = 0;

        // Si el objeto está persistido, utilizar el id
        /*
        if(id != null) {
            result = id.hashCode();
        }
        */

        result = 31 * result + rut.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + birth.hashCode();
        result = 31 * result + gender.hashCode();
        return result;
    }

}
