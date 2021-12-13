package cl.dsoto.StudentService.repositories

import cl.dsoto.StudentService.models.Student
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

import java.time.LocalDate

import static org.assertj.core.api.AssertionsForClassTypes.assertThat

/**
 * Created by root on 10-12-21.
 */
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    StudentRepository underTest;

    static Student student
    static Pageable pageable;;

    @BeforeAll
    public void init() {
        student = new Student("1-1","Juan Pérez", LocalDate.of(2000,1,1),"M");
        underTest.save(student);
        student = new Student("1-9","Carla González", LocalDate.of(2010,2,2),"F");
        underTest.save(student);
        student = new Student("1-1","Marcela Romero", LocalDate.of(2012,2,2),"F");
        underTest.save(student);
        student = new Student("1-9","Juan Pablo Arancibia", LocalDate.of(2013,2,2),"M");
        underTest.save(student);
        student = new Student("1-1","Sofia Guzmán", LocalDate.of(2014,2,2),"F");
        underTest.save(student);

        pageable = PageRequest.of(0, 5, Sort.by("id").descending());
    }

    @Test
    void itShouldMatchName() {
        // given
        // init

        // when
        Page<Student> result = underTest.findAll(pageable, "Juan")

        // then
        assertThat(result.content).
                isEqualTo(Arrays.asList(new Student("1-9","Juan Pablo Arancibia", LocalDate.of(2013,2,2),"M")));
    }

    /*
    @Test
    void itShouldMatchBirth() {
        // given
        Student student
        // when

        // then
    }

    @Test
    void itShouldMatchGender() {
        // given
        Student student
        // when

        // then
    }
    */
}
