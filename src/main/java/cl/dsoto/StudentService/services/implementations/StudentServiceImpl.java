package cl.dsoto.StudentService.services.implementations;

import cl.dsoto.StudentService.dto.extras.GenderPrediction;
import cl.dsoto.StudentService.dto.extras.NationPrediction;
import cl.dsoto.StudentService.dto.extras.StudentAugmented;
import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.dto.extras.AgePrediction;
import cl.dsoto.StudentService.repositories.StudentRepository;
import cl.dsoto.StudentService.services.StudentService;
import cl.dsoto.StudentService.services.external.AgifyService;
import cl.dsoto.StudentService.services.external.GenderizeService;
import cl.dsoto.StudentService.services.external.NationalizeService;
import cl.dsoto.StudentService.services.external.async.AgifyServiceAsync;
import cl.dsoto.StudentService.services.external.async.GenderizeServiceAsync;
import cl.dsoto.StudentService.services.external.async.NationalizeServiceAsync;
import cl.dsoto.StudentService.services.external.impl.proxies.AgifyServiceProxy;
import cl.dsoto.StudentService.services.external.impl.proxies.GenderizeServiceProxy;
import cl.dsoto.StudentService.services.external.impl.proxies.NationalizeServiceProxy;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by root on 10-12-21.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    AgifyService agifyService;

    @Autowired
    GenderizeService genderizeService;

    @Autowired
    NationalizeService nationalizeService;

    /**
     * Asynchronous components
     */
    @Autowired
    AgifyServiceAsync agifyServiceAsync;

    @Autowired
    GenderizeServiceAsync genderizeServiceAsync;

    @Autowired
    NationalizeServiceAsync nationalizeServiceAsync;

    @Autowired
    MeterRegistry meterRegistry;

    /**
     * Proxy components
     */
    @Autowired
    AgifyServiceProxy agifyServiceProxy;

    @Autowired
    GenderizeServiceProxy genderizeServiceProxy;

    @Autowired
    NationalizeServiceProxy nationalizeServiceProxy;


    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Timed("students_paginated_latency")
    public Page<Student> getStudentsPaginated(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> getStudentsPaginated(Pageable pageable, String filter) {
        SpringVersion.getVersion();
        return studentRepository.findAll(pageable, filter);
    }

    @Override
    public Page<StudentAugmented> getStudentsAugmentedPaginated(Pageable pageable) {

        List<StudentAugmented> studentAugmentedList = new ArrayList<>();

        for (Student student : studentRepository.findAll(pageable).getContent()) {

            String name = student.getName().split(" ")[0];

            //AgePrediction agePred = agifyService.getAgePrediction(name);
            AgePrediction agePred = agifyServiceProxy.getAgePrediction(name);
            GenderPrediction genderPred = genderizeServiceProxy.getGenderPrediction(name);
            NationPrediction nationPred = nationalizeServiceProxy.getNationPrediction(name);

            studentAugmentedList.add(new StudentAugmented(student, agePred, genderPred, nationPred));
        }

        Page<StudentAugmented> page = new PageImpl<>(studentAugmentedList,pageable,pageable.getPageSize());

        return page;
    }

    @Override
    public Page<StudentAugmented> getStudentsAugmentedPaginatedPar(Pageable pageable)
            throws ExecutionException, InterruptedException {

        List<StudentAugmented> studentAugmentedList = new ArrayList<>();

        for (Student student : studentRepository.findAll(pageable).getContent()) {

            String name = student.getName().split(" ")[0];

            CompletableFuture<AgePrediction> agePred = agifyServiceAsync.getAgePrediction(name);
            CompletableFuture<GenderPrediction> genderPred = genderizeServiceAsync.getGenderPrediction(name);
            CompletableFuture<NationPrediction> nationPred = nationalizeServiceAsync.getNationPrediction(name);

            studentAugmentedList.add(new StudentAugmented(student, agePred.get(), genderPred.get(), nationPred.get()));
        }

        Page<StudentAugmented> page = new PageImpl<>(studentAugmentedList,pageable,pageable.getPageSize());

        return page;
    }


}
