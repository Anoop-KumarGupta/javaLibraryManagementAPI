package com.example.lms.controller;


import com.example.lms.model.Issued;
import com.example.lms.service.IssuedServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/issued")
public class IssuedController {

    @Autowired
    private IssuedServices issuedServices;

    @GetMapping
    public List<Issued> getAllIssued(){
        return issuedServices.getAllIssued();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Issued getOneIssued(@PathVariable Long id){
        return issuedServices.getOneIssued(id);
    }


    @PostMapping
    public Issued createIssuedDetails(@RequestBody final Issued issued){
        return issuedServices.createIssuedDetails(issued);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public Issued updateIssuedDetails(@PathVariable Long id, @RequestBody final Issued issued){
        return issuedServices.updateIssuedDetails(id,issued);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void deleteIssuedDetails(@PathVariable Long id){
        issuedServices.deleteIssuedDetails(id);
    }
}
