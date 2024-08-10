package com.example.lms.AdminTest;

import com.example.lms.model.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ModelTest {
    @Test
    public void testAdminModelGetterFunctions(){
        Admin admin=new Admin(1L,"Anoop","Intern");
        assertEquals(1L,admin.getAdminId());
        assertEquals("Anoop",admin.getAdminName());
        assertEquals("Intern",admin.getAdminPosition());
    }

    @Test
    public void testAdminModelSetterFunctions(){
        Admin admin=new Admin();
        admin.setAdminId(1L);
        admin.setAdminName("Anoop");
        admin.setAdminPosition("Intern");
        assertEquals(1L,admin.getAdminId());
        assertEquals("Anoop",admin.getAdminName());
        assertEquals("Intern",admin.getAdminPosition());
    }
}
