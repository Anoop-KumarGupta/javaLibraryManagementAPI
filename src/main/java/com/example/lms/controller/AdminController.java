package com.example.lms.controller;


import com.example.lms.dto.AdminDTO;
import com.example.lms.dto.AdminResponeDto;
import com.example.lms.errorHandling.GlobalNotFoundException;
import com.example.lms.model.Admin;
import com.example.lms.dto.AdminCSV;
import com.example.lms.service.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    @Autowired
    private AdminServices adminService;


    @GetMapping
    public List<Admin> getAllAdmins(){
        return adminService.getAllAdmins();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Admin getOneAdmin(@PathVariable Long id){
        try{
            return adminService.getOneAdmin(id);
        }catch (GlobalNotFoundException e){
            throw e;
        }

    }

    @GetMapping
    @RequestMapping("/csvData")
    public List<AdminCSV> getCsvData(){
        return adminService.getCsvData();
    }

    @PostMapping
    public Admin createAdminDetails(@RequestBody final Admin admin) throws Exception {
        return adminService.createAdminDetails(admin);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public String deleteAdmin(@PathVariable Long id){
        return adminService.deleteAdmin(id);
    }

    @RequestMapping(value="{id}",method=RequestMethod.PUT)
    public AdminResponeDto updateAdminDetails(@PathVariable Long id, @RequestBody final AdminDTO admindto){
        return adminService.updateAdminDetails(id,admindto);
    }
}
















