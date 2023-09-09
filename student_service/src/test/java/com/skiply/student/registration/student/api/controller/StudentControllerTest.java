package com.skiply.student.registration.student.api.controller;

import com.skiply.student.registration.common.model.Student;
import com.skiply.student.registration.common.model.StudentGrade;
import com.skiply.student.registration.common.model.StudentStatus;
import com.skiply.student.registration.common.model.id.StudentId;
import com.skiply.student.registration.student.service.StudentCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentCreator studentCreator;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Disabled("because of the feign client calls, which needs to be mocked using a mock web server.")
    @Test
    @DisplayName("GIVEN valid student data WHEN call create student api THEN student create success response is returned")
    void createStudentSuccessTest() throws Exception {
        final var mobile = generateRandomMobile();
        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "John Doe",
                                    "grade": "2",
                                    "mobile": "%s",
                                    "school": "ABC School"
                                }
                                """.formatted(mobile))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "name": "John Doe",
                            "grade": "2",
                            "mobile": "%s",
                            "school": "ABC School",
                            "status": "PENDING_REGISTRATION"
                        }
                        """.formatted(mobile), false))
                .andExpect(jsonPath("$.id").isNotEmpty()) //because the id is dynamic
                .andExpect(jsonPath("$.paymentId").isNotEmpty()); //because the payment id is dynamic
    }

    @Test
    @DisplayName("GIVEN student data with duplicate mobile number WHEN call create student api THEN mobile number duplicated error is returned")
    void createStudentMobileNumberDuplicateTest() throws Exception {
        final var mobile = "0987654321";
        studentCreator.execute(Student.builder()
                .id(StudentId.of(UUID.randomUUID().toString()))
                .name("Tharindu Eranga")
                .grade(StudentGrade.of(1))
                .mobile(mobile)
                .school("XYZ School")
                .status(StudentStatus.PENDING_REGISTRATION)
                .build()
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "John Doe",
                                    "grade": "2",
                                    "mobile": "%s",
                                    "school": "ABC School"
                                }
                                """.formatted(mobile))
                )
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "Errors": [
                                {
                                    "Code": "400 BAD_REQUEST",
                                    "Message": "Mobile number is already registered!"
                                }
                            ]
                        }
                        """, true));
    }

    @Test
    @DisplayName("GIVEN correct student id WHEN call get student by id api THEN student details response is returned")
    void getStudentByIdSuccessTest() throws Exception {
        UUID studentId = UUID.randomUUID();
        var mobile = generateRandomMobile();
        studentCreator.execute(Student.builder()
                .id(StudentId.of(studentId.toString()))
                .name("Tharindu Eranga")
                .grade(StudentGrade.of(1))
                .mobile(mobile)
                .school("XYZ School")
                .status(StudentStatus.PENDING_REGISTRATION)
                .build()
        );
        mockMvc.perform(MockMvcRequestBuilders.get("/students/%s".formatted(studentId))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "id": "%s",
                            "name": "Tharindu Eranga",
                            "grade": "1",
                            "mobile": "%s",
                            "school": "XYZ School",
                            "status": "PENDING_REGISTRATION"
                        }
                        """.formatted(studentId, mobile), true));
    }

    @Test
    @DisplayName("GIVEN incorrect student id WHEN call get student by id api THEN bad request response is returned")
    void getStudentByIdIncorrectIdTest() throws Exception {
        UUID studentId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/students/%s".formatted(studentId))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "Errors": [
                                {
                                    "Code": "400 BAD_REQUEST",
                                    "Message": "Student not found for the id: %s"
                                }
                            ]
                        }
                        """.formatted(studentId.toString()), true));
    }

    @Test
    @DisplayName("GIVEN invalid student id WHEN call get student by id api THEN bad request response is returned")
    void getStudentByIdInvalidIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students/%s".formatted("12345"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "Errors": [
                                {
                                    "Code": "400 BAD_REQUEST",
                                    "Message": "Invalid UUID string: 12345"
                                }
                            ]
                        }
                        """, true));
    }


    public static String generateRandomMobile() {
        final int length = 10;
        final var random = new Random();
        final var stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }
}