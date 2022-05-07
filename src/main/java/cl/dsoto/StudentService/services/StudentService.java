package cl.dsoto.StudentService.services;

import cl.dsoto.StudentService.dto.extras.StudentAugmented;
import cl.dsoto.StudentService.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by root on 10-12-21.
 */
public interface StudentService {

    List<Student> getAll();

    Page<Student> getStudentsPaginated(Pageable pageable);

    Page<Student> getStudentsPaginated(Pageable pageable, String filter);

    Page<StudentAugmented> getStudentsAugmentedPaginated(Pageable pageable);

    Page<StudentAugmented> getStudentsAugmentedPaginatedPar(Pageable pageable) throws ExecutionException, InterruptedException;

}
