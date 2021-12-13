package cl.dsoto.StudentService.services.implementations;

import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.repositories.StudentRepository;
import cl.dsoto.StudentService.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by root on 10-12-21.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Page<Student> getStudentsPaginated(Pageable pageable) {

        return studentRepository.findAll(pageable);

    }

    @Override
    public Page<Student> getStudentsPaginated(Pageable pageable, String filter) {

        return studentRepository.findAll(filter, pageable);

    }
}
