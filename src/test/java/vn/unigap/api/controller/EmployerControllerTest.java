package vn.unigap.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.EmployerUpdateDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.entity.jpa.JobProvince;
import vn.unigap.api.service.EmployerService;
import vn.unigap.common.pagination.dto.in.PageDtoIn;
import vn.unigap.common.pagination.dto.out.PageDtoOut;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@WebMvcTest(EmployerController.class)
public class EmployerControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private EmployerService employerService;

        @BeforeEach
        public void setup() {
                MockitoAnnotations.openMocks(this);
        }

        public static String asJsonString(final Object obj) {
                try {
                        return new ObjectMapper().writeValueAsString(obj);
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }

        @Test
        public void testCreateEmployer() throws Exception {
                // Prepare test data
                EmployerDtoIn employerDtoIn = new EmployerDtoIn("test4@example.com", "Test Employer", 1,
                                "Test description");
                EmployerDtoOut mockEmployer = new EmployerDtoOut(1L, employerDtoIn.getEmail(), employerDtoIn.getName(),
                                new JobProvince(employerDtoIn.getProvinceId(), "Hồ Chí Minh"),
                                employerDtoIn.getDescription());

                // Mocking service method
                when(employerService.createEmployer(any(EmployerDtoIn.class))).thenReturn(mockEmployer);

                // Perform POST request and assert results
                mockMvc.perform(post("/api/v1/employers").contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(employerDtoIn))).andExpect(status().isCreated())
                                .andExpect(jsonPath("$.errorCode").value(0))
                                .andExpect(jsonPath("$.statusCode").value(201))
                                .andExpect(jsonPath("$.message").value("Employer has been created successfully"))
                                .andExpect(jsonPath("$.object.id").value(1))
                                .andExpect(jsonPath("$.object.email").value(employerDtoIn.getEmail()))
                                .andExpect(jsonPath("$.object.name").value(employerDtoIn.getName()))
                                .andExpect(jsonPath("$.object.province.id").value(employerDtoIn.getProvinceId()))
                                .andExpect(jsonPath("$.object.province.name").value("Hồ Chí Minh"))
                                .andExpect(jsonPath("$.object.description").value(employerDtoIn.getDescription()));

                // Verify service method was called with the correct arguments
                verify(employerService).createEmployer(employerDtoIn);
        }

        @Test
        public void testUpdateEmployer() throws Exception {
                // Prepare test data
                EmployerUpdateDtoIn employerUpdateDtoIn = new EmployerUpdateDtoIn("Update Test Employer", 1,
                                "Update description");
                EmployerDtoOut mockEmployer = new EmployerDtoOut(1L, "test4@example.com", employerUpdateDtoIn.getName(),
                                new JobProvince(employerUpdateDtoIn.getProvince(), "Hồ Chí Minh"),
                                employerUpdateDtoIn.getDescription());

                // Mocking service method
                when(employerService.updateEmployer(anyLong(), any(EmployerUpdateDtoIn.class)))
                                .thenReturn(mockEmployer);

                // Perform PATCH request and assert results
                mockMvc.perform(patch("/api/v1/employers/1").contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(employerUpdateDtoIn))).andExpect(status().isOk())
                                .andExpect(jsonPath("$.errorCode").value(0))
                                .andExpect(jsonPath("$.statusCode").value(200))
                                .andExpect(jsonPath("$.message").value("Employer has been updated successfully"))
                                .andExpect(jsonPath("$.object.id").value(1))
                                .andExpect(jsonPath("$.object.email").value("test4@example.com"))
                                .andExpect(jsonPath("$.object.name").value(employerUpdateDtoIn.getName()))
                                .andExpect(jsonPath("$.object.province.id").value(employerUpdateDtoIn.getProvince()))
                                .andExpect(jsonPath("$.object.province.name").value("Hồ Chí Minh"))
                                .andExpect(jsonPath("$.object.description")
                                                .value(employerUpdateDtoIn.getDescription()));

                // Verify service method was called with the correct arguments
                verify(employerService).updateEmployer(1L, employerUpdateDtoIn);
        }

        @Test
        public void testGetEmployerById() throws Exception {
                // Prepare test data
                JobProvince province = new JobProvince(1, "Hồ Chí Minh");
                EmployerDtoOut mockEmployer = new EmployerDtoOut(3093725L,
                                "27f9bad99edfff6434d80a334016d975b3ba1e91@sieu-viet.com", "3shomes", province,
                                "vận hành căn hộ cho thuê");

                // Mocking service method
                when(employerService.getEmployerById(anyLong())).thenReturn(mockEmployer);

                // Perform GET request and assert results
                mockMvc.perform(get("/api/v1/employers/3093725").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andExpect(jsonPath("$.errorCode").value(0))
                                .andExpect(jsonPath("$.statusCode").value(200))
                                .andExpect(jsonPath("$.message").value("Employer has been retrieved successfully"))
                                .andExpect(jsonPath("$.object.id").value(3093725))
                                .andExpect(jsonPath("$.object.email")
                                                .value("27f9bad99edfff6434d80a334016d975b3ba1e91@sieu-viet.com"))
                                .andExpect(jsonPath("$.object.name").value("3shomes"))
                                .andExpect(jsonPath("$.object.province.id").value(1))
                                .andExpect(jsonPath("$.object.province.name").value("Hồ Chí Minh"))
                                .andExpect(jsonPath("$.object.description").value("vận hành căn hộ cho thuê"));
        }

        @Test
        public void testGetAllEmployers() throws Exception {
                // Prepare test data
                PageDtoIn pageDtoIn = new PageDtoIn(1, 10);
                PageDtoOut<EmployerDtoOut> mockPageDtoOut = new PageDtoOut<EmployerDtoOut>(1, 10, 20L, 2L,
                                Arrays.asList(new EmployerDtoOut(1L, "test1@example.com", "Employer 1",
                                                new JobProvince(1, "Hồ Chí Minh"), "Description 1"),
                                                new EmployerDtoOut(2L, "test2@example.com", "Employer 2",
                                                                new JobProvince(2, "Hà Nội"), "Description 2")));

                // Mocking service method
                when(employerService.getAllEmployers(any(PageDtoIn.class))).thenReturn(mockPageDtoOut);

                // Perform GET request and assert results
                mockMvc.perform(get("/api/v1/employers").contentType(MediaType.APPLICATION_JSON)
                                .param("page", String.valueOf(pageDtoIn.getPage()))
                                .param("size", String.valueOf(pageDtoIn.getSize()))).andExpect(status().isOk())
                                .andExpect(jsonPath("$.errorCode").value(0))
                                .andExpect(jsonPath("$.statusCode").value(200))
                                .andExpect(jsonPath("$.message").value("Employers have been retrieved successfully"))
                                .andExpect(jsonPath("$.object.page").value(1))
                                .andExpect(jsonPath("$.object.pageSize").value(10))
                                .andExpect(jsonPath("$.object.totalElements").value(20))
                                .andExpect(jsonPath("$.object.totalPages").value(2))
                                .andExpect(jsonPath("$.object.data[0].id").value(1L))
                                .andExpect(jsonPath("$.object.data[0].email").value("test1@example.com"))
                                .andExpect(jsonPath("$.object.data[0].name").value("Employer 1"))
                                .andExpect(jsonPath("$.object.data[0].province.id").value(1L))
                                .andExpect(jsonPath("$.object.data[0].province.name").value("Hồ Chí Minh"))
                                .andExpect(jsonPath("$.object.data[0].description").value("Description 1"))
                                .andExpect(jsonPath("$.object.data[1].id").value(2L))
                                .andExpect(jsonPath("$.object.data[1].email").value("test2@example.com"))
                                .andExpect(jsonPath("$.object.data[1].name").value("Employer 2"))
                                .andExpect(jsonPath("$.object.data[1].province.id").value(2L))
                                .andExpect(jsonPath("$.object.data[1].province.name").value("Hà Nội"))
                                .andExpect(jsonPath("$.object.data[1].description").value("Description 2"));

                // Verify service method was called with the correct arguments
                verify(employerService).getAllEmployers(pageDtoIn);
        }

        @Test
        public void testDeleteEmployerById() throws Exception {
                // Prepare test data
                long employerId = 1L;

                // Perform DELETE request and assert results
                mockMvc.perform(delete("/api/v1/employers/{id}", employerId)).andExpect(status().isOk())
                                .andExpect(jsonPath("$.errorCode").value(0))
                                .andExpect(jsonPath("$.statusCode").value(200))
                                .andExpect(jsonPath("$.message").value("Employer has been deleted successfully"));
        }
}