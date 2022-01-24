package cl.dsoto.StudentService.services;

import cl.dsoto.StudentService.dto.extras.StudentAugmented;
import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * Created by root on 10-12-21.
 */
public interface UserService {

    User authenticate(String username, String password) throws Exception;

    User create(String username, String password) throws Exception;

}
