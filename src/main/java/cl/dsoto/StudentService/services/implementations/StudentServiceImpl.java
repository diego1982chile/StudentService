package cl.dsoto.StudentService.services.implementations;

import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.repositories.StudentRepository;
import cl.dsoto.StudentService.services.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by root on 10-12-21.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Page<Student> getStudentsPaginated(Pageable pageable) {

        return studentRepository.findAll(pageable);

    }

    @Override
    public Page<Student> getStudentsPaginated(Pageable pageable, String filter) {

        return studentRepository.findAll(pageable, filter);

    }
}
