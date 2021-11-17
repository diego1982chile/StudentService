package cl.dsoto.StudentService.repositories;

import cl.dsoto.StudentService.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by root on 17-11-21.
 */
public interface StudentRepository extends PagingAndSortingRepository<Student, Integer> {

    @Query("SELECT u FROM Student u WHERE lower(u.rut) like concat('%',lower(:filter),'%') " +
            "or lower(u.name) like concat('%',lower(:filter),'%') or lower(u.birth) like concat('%',lower(:filter),'%') " +
            "or lower(u.gender) like concat('%',lower(:filter),'%')")
    Page<Student> findAll(@Param("filter") String filter, Pageable pageable);
}
