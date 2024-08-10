package com.example.lms.service;

import com.example.lms.dto.AdminDTO;
import com.example.lms.dto.AdminResponeDto;
import com.example.lms.errorHandling.GlobalNotFoundException;
import com.example.lms.model.Admin;
import com.example.lms.dto.AdminCSV;
import com.example.lms.repositories.AdminRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;



@Service
public class AdminServices {

    @Autowired
    private AdminRepository adminRepository;


    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }


    public Admin getOneAdmin(@PathVariable Long id){
        if(adminRepository.findByAdminId(id)== null){
            throw new GlobalNotFoundException("Admin Id is not found "+id);
        }
        return adminRepository.findByAdminId(id);
    }

    public Admin createAdminDetails(@RequestBody final Admin admin) throws Exception {
        try{
            return adminRepository.saveAndFlush(admin);
        }catch(Exception e){
            throw new Exception("Admin Not created, because of the mentioned reasons. Please Try again! Error message: "+e.getMessage());
        }
    }


    public String deleteAdmin(@PathVariable Long id){
        Admin admin=adminRepository.findByAdminId(id);
        if(admin==null){
            throw new GlobalNotFoundException("Admin does not exist.");
        }
        adminRepository.deleteById(id);
        return "Admin Deleted successfully";
    }


    public AdminResponeDto updateAdminDetails(@PathVariable Long id, @RequestBody final AdminDTO admindto){
        Admin existingAdmin=adminRepository.findByAdminId(id);
        if(existingAdmin==null){
            throw new GlobalNotFoundException("Admin does not exist. Make sure you are entering the correct id number");
        }
        existingAdmin.setAdminPosition(admindto.getAdminPosition());
        existingAdmin.setAdminName(admindto.getAdminName());
        Admin updateAdmin= adminRepository.saveAndFlush(existingAdmin);

        AdminResponeDto adminResponeDto1 = new AdminResponeDto();
        adminResponeDto1.setAdminDescription(updateAdmin.getAdminName()+updateAdmin.getAdminPosition());
        adminResponeDto1.setAdminName(updateAdmin.getAdminName());
        adminResponeDto1.setAdminPosition(updateAdmin.getAdminPosition());
        return adminResponeDto1;
    }

    // implementing functionality for reading the csv file and then uploading the data to the database
    public File readFile(){
        File file = null;
        try{
            file= ResourceUtils.getFile("classpath:csv/import/admin.csv");
        }catch(FileNotFoundException e){
            throw new RuntimeException("Could not get data due the following reason: "+e.getMessage(),e);
        }
        return file;
    }
    // converting csv file to AdminCSV object
    public List<AdminCSV> convertCSV(File csvFile){
        try{
            List<AdminCSV> adminCsvRec= new CsvToBeanBuilder< AdminCSV >(new FileReader(csvFile)).withType(AdminCSV.class).build().parse();
            return adminCsvRec;
        }catch(FileNotFoundException e){
            throw new RuntimeException("Not able to perform the operation due to the following reason :"+e.getMessage(),e);
        }
    }
    public void saveCSV(List<AdminCSV> adminCsv){
        adminCsv.forEach(adminCsvRec->{
            Admin admin=new Admin();
            admin.setAdminName(adminCsvRec.getAdminName());
            admin.setAdminPosition(adminCsvRec.getAdminPosition());
            adminRepository.save(admin);
        });
    }
    public List<AdminCSV> getCsvData() {
        File file=readFile();
        List<AdminCSV> adminCsvRecords=convertCSV(file);
        saveCSV(adminCsvRecords);
        return adminCsvRecords;
    }
}
