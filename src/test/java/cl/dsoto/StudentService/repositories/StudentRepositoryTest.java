package cl.dsoto.StudentService.repositories;

import cl.dsoto.StudentService.models.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.SpringVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by root on 10-12-21.
 */
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    private Student student;
    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        SpringVersion.getVersion();
        student = new Student("1-1","Juan Pérez", LocalDate.of(2000,1,1),"M");
        underTest.save(student);
        student = new Student("1-2","Carla González", LocalDate.of(2010,2,2),"F");
        underTest.save(student);
        student = new Student("1-3","Marcela Romero", LocalDate.of(2012,2,2),"F");
        underTest.save(student);
        student = new Student("1-4","Juan Pablo Arancibia", LocalDate.of(2013,2,2),"M");
        underTest.save(student);
        student = new Student("1-5","Sofia Guzmán", LocalDate.of(2014,2,2),"F");
        underTest.save(student);

        pageable = PageRequest.of(0, 5, Sort.by("id").descending());
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldMatchName() {

        // when
        List<Student> result = underTest.findAll(pageable, "Juan").getContent();

        // then
        List<Student> list = Arrays.asList(
                new Student("1-1","Juan Pérez", LocalDate.of(2000,1,1),"M"),
                new Student("1-4","Juan Pablo Arancibia", LocalDate.of(2013,2,2),"M"));

        assertThat(result).asList().hasSameElementsAs(list);
    }

    @Test
    void itShouldMatchBirth() {

        // when
        List<Student> result = underTest.findAll(pageable, LocalDate.of(2014,2,2).toString()).getContent();

        // then
        List<Student> list = Collections.singletonList(
                new Student("1-5", "Sofia Guzmán", LocalDate.of(2014, 2, 2), "F"));

        assertThat(result).asList().hasSameElementsAs(list);
    }

    @Test
    void itShouldMatchRut() {

        // when
        List<Student> result = underTest.findAll(pageable, "1-5").getContent();

        // then
        List<Student> list = Collections.singletonList(
                new Student("1-5", "Sofia Guzmán", LocalDate.of(2014, 2, 2), "F"));

        assertThat(result).asList().hasSameElementsAs(list);
    }

}
