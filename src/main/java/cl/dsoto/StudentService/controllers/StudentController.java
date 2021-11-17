package cl.dsoto.StudentService.controllers;


import cl.dsoto.StudentService.models.CustomPage;
import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

;


/**
 * Created by des01c7 on 12-12-19.
 */
@RestController
@RequestMapping("students")
public class StudentController {

    static private final Logger logger = Logger.getLogger(StudentController.class.getName());

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/")
    public List<Student> getStudentsPaginated() {

        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());

        return studentRepository.findAll(pageable).getContent();
    }

    @GetMapping("{page}/{size}/{sort}/{desc}")
    public CustomPage getStudentsPaginated(@PathVariable int page,
                                              @PathVariable int size,
                                              @PathVariable String sort,
                                              @PathVariable String desc) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        switch (desc) {
            case "desc":
                pageable = PageRequest.of(page, size, Sort.by(sort).descending());
                break;
            case "asc":
                pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
                break;
        }

        Page<Student> pag = studentRepository.findAll(pageable);

        return new CustomPage(pag.getContent(), pag.getTotalElements(), pag.getNumberOfElements(), true, pag.getSize(), pag.getNumber());
    }

}
