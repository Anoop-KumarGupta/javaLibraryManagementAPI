package com.example.lms.AdminTest;

import com.example.lms.controller.AdminController;
import com.example.lms.dto.AdminDTO;
import com.example.lms.dto.AdminResponeDto;
import com.example.lms.errorHandling.GlobalNotFoundException;
import com.example.lms.model.Admin;
import com.example.lms.service.AdminServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
public class ControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AdminServices adminService;

    @InjectMocks
    private AdminController adminController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        objectMapper = new ObjectMapper();
    }


    // testing whether i am getting all admin details properly or not
    @Test
    public void testGetAllAdmins() throws Exception {
        Admin admin1 = new Admin(1L, "Admin1","Intern");
        Admin admin2 = new Admin(2L, "Admin2","Intern");
        List<Admin> admins = Arrays.asList(admin1, admin2);
        when(adminService.getAllAdmins()).thenReturn(admins);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admins"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].adminId", is(1)))
                .andExpect(jsonPath("$[0].adminName", is("Admin1")))
                .andExpect(jsonPath("$[0].adminPosition", is("Intern")))
                .andExpect(jsonPath("$[1].adminId", is(2)))
                .andExpect(jsonPath("$[1].adminName", is("Admin2")))
                .andExpect(jsonPath("$[0].adminPosition", is("Intern")))
                .andDo(print());
    }

    // testing whether i am able to get the detail of one admin or not
    @Test
    public void testGetOneAdmin() throws Exception {
        Admin admin = new Admin(1L, "Admin1","Intern");
        when(adminService.getOneAdmin(1L)).thenReturn(admin);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admins/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.adminId", is(1)))
                .andExpect(jsonPath("$.adminName", is("Admin1")))
                .andExpect(jsonPath("$.adminPosition",is("Intern")))
                .andDo(print());
    }


    // testing for creating a new admin details
    @Test
    public void testCreateAdminDetails() throws Exception{
        Admin admin = new Admin();
        admin.setAdminId(1L);
        admin.setAdminName("John Doe");
        admin.setAdminPosition("Manager");

        when(adminService.createAdminDetails(any(Admin.class))).thenReturn(admin);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adminId").value(1L))
                .andExpect(jsonPath("$.adminName").value("John Doe"))
                .andExpect(jsonPath("$.adminPosition").value("Manager"))
                .andDo(print());
    }


    // testing for deleting an admin detail
    @Test
    public void testDeleteAdminDetails() throws Exception {
        Long adminId = 1L;
        when(adminService.deleteAdmin(any(Long.class))).thenReturn("Admin deleted successfully");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admins/{id}", adminId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Admin deleted successfully"))
                .andDo(print());
    }




    //  testing for updating the details of admin
    @Test
    public void testUpdateAdminDetails() throws Exception {
        Long adminId = 1L;
        AdminDTO adminDto = new AdminDTO();
        adminDto.setAdminName("Anoop");
        adminDto.setAdminPosition("Intern");

        AdminResponeDto responseDto = new AdminResponeDto();
        responseDto.setAdminDescription(responseDto.getAdminName()+"'s details has been successfully updated");

        when(adminService.updateAdminDetails(any(Long.class), any(AdminDTO.class))).thenReturn(responseDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admins/{id}", adminId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(responseDto)));
    }

//    @Test
//public void testGetOneAdminWhenNotFound() throws Exception {
//
//    Long adminId = 1L;
//    when(adminService.getOneAdmin(adminId)).thenThrow(new GlobalNotFoundException("Admin Id is not found "+ adminId));
//
//
//    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admins/{id}", adminId))
//            .andExpect(status().isNotFound())
//            .andExpect(result -> assertTrue(result.getResolvedException() instanceof GlobalNotFoundException))
//            .andExpect(result -> assertEquals("Admin Id is not found " + adminId, result.getResolvedException().getMessage()));
//}
}
