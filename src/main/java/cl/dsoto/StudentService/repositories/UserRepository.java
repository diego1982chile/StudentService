package cl.dsoto.StudentService.repositories;

import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 * Created by dsoto on 17-11-21.
 */

/**
 * Repositorio para entidad Student
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Método personalizado para realizar una búsqueda utilizando un filtro
     * @param username
     * @return
     */
    User findByUsername(@Param("username") String username);
}
