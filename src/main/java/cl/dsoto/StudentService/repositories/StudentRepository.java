package cl.dsoto.StudentService.repositories;

import cl.dsoto.StudentService.models.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by root on 17-11-21.
 */
public interface StudentRepository extends PagingAndSortingRepository<Student, Integer> {
//public interface StudentRepository extends JpaRepository<Student, Long> {

}
