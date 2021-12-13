package cl.dsoto.StudentService.services;

import cl.dsoto.StudentService.models.MyPageResponse;
import cl.dsoto.StudentService.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by root on 10-12-21.
 */
public interface StudentService {

    Page<Student> getStudentsPaginated(Pageable pageable);

    Page<Student> getStudentsPaginated(Pageable pageable, String filter);

}
