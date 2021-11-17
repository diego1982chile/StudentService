package cl.dsoto.StudentService.controllers;


import cl.dsoto.StudentService.models.MyPageRequest;
import cl.dsoto.StudentService.models.MyPageResponse;
import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

;


/**
 * Created by des01c7 on 12-12-19.
 */
@RestController
@CrossOrigin(origins = "*")
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
    public MyPageResponse getStudentsPaginated(@PathVariable int page,
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

        Page<Student> results = studentRepository.findAll(pageable);

        return new MyPageResponse(results.getContent(), results.getTotalElements(), results.getNumberOfElements());
    }

    @PostMapping
    public MyPageResponse getStudentsPaginated(@RequestBody MyPageRequest myPageRequest) {

        Pageable pageable = PageRequest.of(myPageRequest.getPage(), myPageRequest.getFetchSize(), Sort.by("id").ascending());

        if(myPageRequest.getSort() != null) {
            if(myPageRequest.getSortDir().equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(myPageRequest.getPage(), myPageRequest.getFetchSize(), Sort.by(myPageRequest.getSort()).ascending());
            }
            else {
                pageable = PageRequest.of(myPageRequest.getPage(), myPageRequest.getFetchSize(), Sort.by(myPageRequest.getSort()).descending());
            }
        }

        Page<Student> results = studentRepository.findAll(pageable);

        if(myPageRequest.getFilter() != null) {
            results = studentRepository.findAll(myPageRequest.getFilter(), pageable);
        }

        return new MyPageResponse(results.getContent(), results.getTotalElements(), results.getNumberOfElements());
    }

}
