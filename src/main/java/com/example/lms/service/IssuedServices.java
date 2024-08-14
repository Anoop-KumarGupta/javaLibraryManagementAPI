package com.example.lms.service;

import com.example.lms.errorHandling.GlobalNotFoundException;
import com.example.lms.model.Book;
import com.example.lms.model.Issued;
import com.example.lms.model.Member;
import com.example.lms.repositories.BookRepository;
import com.example.lms.repositories.IssuedRepository;
import com.example.lms.repositories.MemberRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class IssuedServices {
    @Autowired
    private IssuedRepository issuedRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public List<Issued> getAllIssued(){
        return issuedRepository.findAll();
    }


    public Issued getOneIssued(Long id){
        if(issuedRepository.findByIssuedId(id)==null){
            throw new GlobalNotFoundException("Issued id does not exist.");
        }
        return issuedRepository.findByIssuedId(id);
    }

    // logic can be improved
    public Issued createIssuedDetails(Issued issued){
        Member member=memberRepository.save(issued.getMember());
        issued.setMember(member);
        Book book=bookRepository.save(issued.getBook());
        issued.setBook(book);
        return issuedRepository.saveAndFlush(issued);
    }

    public Issued updateIssuedDetails(Long id,Issued issued){
        if(issuedRepository.findByIssuedId(id)==null){
            throw new GlobalNotFoundException("Issued id does not exist");
        }
        Issued existingIssuedDetails=issuedRepository.findByIssuedId(id);
        BeanUtils.copyProperties(issued,existingIssuedDetails,"issued_id");
        return issuedRepository.saveAndFlush(existingIssuedDetails);
    }

    public void deleteIssuedDetails(Long id){
        if(issuedRepository.findByIssuedId(id)==null){
            throw new GlobalNotFoundException("Issued id does not exist");
        }
        issuedRepository.deleteById(id);
    }
}
