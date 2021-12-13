package cl.dsoto.StudentService.services.implementations;

import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.repositories.StudentRepository;
import cl.dsoto.StudentService.services.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.mockito.Mockito.*;;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentService underTest;

    private Pageable pageable;

    @BeforeEach
    void setUp() {
        underTest = new StudentServiceImpl(studentRepository);
        pageable = PageRequest.of(0, 1);
    }

    @Test
    void canGetStudentsPaginated() {
        // when
        underTest.getStudentsPaginated(pageable);
        // then
        ArgumentCaptor<Pageable> pageableArgumentCaptor =
                ArgumentCaptor.forClass(Pageable.class);

        verify(studentRepository).findAll(pageableArgumentCaptor.capture());
    }

    @Test
    void canGetStudentsPaginatedFiltered() {
        // when
        underTest.getStudentsPaginated(pageable, "1-1");
        // then
        ArgumentCaptor<Pageable> pageableArgumentCaptor =
                ArgumentCaptor.forClass(Pageable.class);

        ArgumentCaptor<String> stringArgumentCaptor =
                ArgumentCaptor.forClass(String.class);

        verify(studentRepository).findAll(pageableArgumentCaptor.capture(), stringArgumentCaptor.capture());
    }
}