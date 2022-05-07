package cl.dsoto.StudentService.repositories;

import cl.dsoto.StudentService.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dsoto on 17-11-21.
 */

/**
 * Repositorio para entidad Student
 */
public interface StudentRepository extends PagingAndSortingRepository<Student, Integer> {

    /**
     * Método personalizado para realizar una búsqueda utilizando un filtro
     * @param filter
     * @param pageable
     * @return
     */
    @Query("SELECT u FROM Student u WHERE lower(u.rut) like concat('%',lower(:filter),'%') " +
            "or lower(u.name) like concat('%',lower(:filter),'%') or lower(u.birth) like concat('%',lower(:filter),'%') " +
            "or lower(u.gender) like concat('%',lower(:filter),'%')")
    Page<Student> findAll(Pageable pageable, @Param("filter") String filter);

    @Override
    List<Student> findAll();
}
