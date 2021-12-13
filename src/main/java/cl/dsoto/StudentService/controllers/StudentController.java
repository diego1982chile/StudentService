package cl.dsoto.StudentService.controllers;


import cl.dsoto.StudentService.models.CustomPageResponse;
import cl.dsoto.StudentService.models.MyPageRequest;
import cl.dsoto.StudentService.models.MyPageResponse;
import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.repositories.StudentRepository;
import cl.dsoto.StudentService.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

;


/**
 * Created by dsoto on 12-12-19.
 */

/**
 * Endpoint para API students
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping("/")
    public List<Student> getStudentsPaginated() {

        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());

        return studentService.getStudentsPaginated(pageable).getContent();
    }


    @GetMapping
    public CustomPageResponse getStudentsPaginated(@RequestParam int page,
                                               @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        Page<Student> results = studentService.getStudentsPaginated(pageable);

        return new CustomPageResponse(results.getContent(), results.getTotalElements(), results.getTotalPages(), page);
    }

    /**
     * WebMethod responsable de entregar los resultados de acuerdo a los requerimientos especificados en la solicitud de
     * página
     * @param myPageRequest
     * @return MyPageResponse
     */
    @PostMapping
    public MyPageResponse getStudentsPaginated(@RequestBody MyPageRequest myPageRequest) {

        Pageable pageable = PageRequest.of(myPageRequest.getPage(), myPageRequest.getFetchSize(), Sort.by("id").ascending());

        // Si viene especificado el ordenamiento, setear esta información en la búsqueda
        if(myPageRequest.getSort() != null) {
            if(myPageRequest.getSortDir().equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(myPageRequest.getPage(), myPageRequest.getFetchSize(), Sort.by(myPageRequest.getSort()).ascending());
            }
            else {
                pageable = PageRequest.of(myPageRequest.getPage(), myPageRequest.getFetchSize(), Sort.by(myPageRequest.getSort()).descending());
            }
        }

        Page<Student> results = studentService.getStudentsPaginated(pageable, myPageRequest.getFilter());

        return new MyPageResponse(results.getContent(), results.getTotalElements(), results.getNumberOfElements());
    }

}
