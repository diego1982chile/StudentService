package cl.dsoto.StudentService.controllers;


import cl.dsoto.StudentService.dto.extras.CustomPageResponse;
import cl.dsoto.StudentService.dto.MyPageRequest;
import cl.dsoto.StudentService.dto.MyPageResponse;
import cl.dsoto.StudentService.dto.extras.StudentAugmented;
import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.services.StudentService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @GetMapping("/{page}/{size}")
    public List<Student> getStudentsPaginated(@PathVariable int page, @PathVariable int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        List<Student> studentAugmentedList = new ArrayList<>();

        studentAugmentedList = studentService.getStudentsPaginated(pageable).getContent();
        return studentAugmentedList;
    }

    @GetMapping("augmented/{page}/{size}")
    public List<StudentAugmented> getStudentsAugmentedPaginated(@PathVariable int page, @PathVariable int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        List<StudentAugmented> studentAugmentedList = new ArrayList<>();

        studentAugmentedList = studentService.getStudentsAugmentedPaginated(pageable).getContent();
        return studentAugmentedList;
    }


    @GetMapping("augmented/parallel/{page}/{size}")
    public List<StudentAugmented> getStudentsAugmentedPaginatedPar(@PathVariable int page, @PathVariable int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        List<StudentAugmented> studentAugmentedList = new ArrayList<>();

        try {
            studentAugmentedList = studentService.getStudentsAugmentedPaginatedPar(pageable).getContent();
            return studentAugmentedList;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return studentAugmentedList;

    }

    /*
    @GetMapping
    public CustomPageResponse getStudentsPaginated(@RequestParam int page,
                                                   @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        Page<Student> results = studentService.getStudentsPaginated(pageable);

        return new CustomPageResponse(results.getContent(), results.getTotalElements(), results.getTotalPages(), page);
    }
    */

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

        Page<Student> results;

        if(myPageRequest.getFilter() == null) {
            results = studentService.getStudentsPaginated(pageable);
        }
        else {
            results = studentService.getStudentsPaginated(pageable, myPageRequest.getFilter());
        }

        return new MyPageResponse(results.getContent(), results.getTotalElements(), results.getNumberOfElements());
    }

    private void increaseCount(String partnerId, String state) {
        // Counter class stores the measurement name and the tags and
        // their values
        Counter counter = Metrics.counter("request.orders", "partnerId", partnerId, "state", state);
        counter.increment();
    }


}
