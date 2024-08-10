package com.example.lms.controller;


import com.example.lms.model.Member;
import com.example.lms.service.MemberServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    @Autowired
    private MemberServices memberServices;

    @GetMapping
    public List<Member> getAllMember(){
        return memberServices.getAllMember();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Member getOneMember(@PathVariable Long id){
        return memberServices.getOneMember(id);
    }

    @PostMapping
    public Member createMemberDetails(@RequestBody final Member member) throws Exception {
        return memberServices.createMemberDetails(member);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public Member updateMemberDetails(@PathVariable Long id, @RequestBody final Member member){
        return memberServices.updateMemberDetails(id,member);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void deleteMemberDetails(@PathVariable Long id){
        memberServices.deleteMemberDetails(id);
    }
}

