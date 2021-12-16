package cl.dsoto.StudentService.controllers;

import cl.dsoto.StudentService.models.MyPageRequest;
import cl.dsoto.StudentService.models.MyPageResponse;
import cl.dsoto.StudentService.models.Student;
import cl.dsoto.StudentService.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class StudentControllerTest {

    private final static String URI = "/api/students";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private MyPageResponse myPageResponse;

    private Pageable pageable;

    private Page<Student> students1;

    @BeforeEach
    void setUp() {
        List<Student> students = Arrays.asList(
                new Student("1-1","Juan Pérez", LocalDate.of(2000,1,1),"M"),
                new Student("1-2","Carla González", LocalDate.of(2010,2,2),"F"),
                new Student("1-3","Marcela Romero", LocalDate.of(2012,2,2),"F"),
                new Student("1-4","Juan Pablo Arancibia", LocalDate.of(2013,2,2),"M"),
                new Student("1-5","Sofia Guzmán", LocalDate.of(2014,2,2),"F")
        );

        pageable = PageRequest.of(0, 1);
        students1 = Mockito.mock(Page.class);
        when(studentService.getStudentsPaginated(pageable)).thenReturn(students1);
    }

    @Test
    void canGetStudentsPaginated() throws Exception {
        // given
        given(studentService.getStudentsPaginated(pageable)).willReturn(students1);

        Page<Student> page = new PageImpl<Student>(Arrays.asList(
                new Student("1-1","Juan Pérez", LocalDate.of(2000,1,1),"M"),
                new Student("1-2","Carla González", LocalDate.of(2010,2,2),"F")),pageable,5);


        MyPageResponse myPageResponse = MyPageResponse.builder()
                .items(Arrays.asList(
                new Student("1-1","Juan Pérez", LocalDate.of(2000,1,1),"M"),
                new Student("1-2","Carla González", LocalDate.of(2010,2,2),"F")))
                .totalResults(5)
                .limit(2)
                .build();

        when(studentService.getStudentsPaginated(pageable)).thenReturn(page);

        MyPageRequest myPageRequest = MyPageRequest.builder()
                .page(0)
                .fetchSize(1)
                .build();

        // Execute the POST request
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(myPageRequest)))
                        .andExpect(status().isOk())
                        .andExpect(content().json(asJsonString(students1)));

        // then
    }

    @Test
    void testGetStudentsPaginated() {
    }

    @Test
    void testGetStudentsPaginated1() {
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}