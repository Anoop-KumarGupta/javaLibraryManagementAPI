package com.example.lms.service;

import com.example.lms.errorHandling.GlobalNotFoundException;
import com.example.lms.model.Member;
import com.example.lms.repositories.MemberRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class MemberServices {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public List<Member> getAllMember(){
        return memberRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Member getOneMember(@PathVariable Long id){
        if(memberRepository.findByMemberId(id)==null){
            throw new GlobalNotFoundException("Member does not exist!");
        }
        return memberRepository.findByMemberId(id);
    }

    @PostMapping
    public Member createMemberDetails(@RequestBody final Member member) throws Exception {
        try {
            return memberRepository.saveAndFlush(member);
        }catch(Exception e){
            throw new Exception("Could not create the member due to following reasons "+e.getMessage(),e);
        }
    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public Member updateMemberDetails(@PathVariable Long id, @RequestBody final Member member){
        Member existingMemberDetails=memberRepository.findByMemberId(id);
        if(existingMemberDetails==null){
            throw new GlobalNotFoundException("Member does not exist!");
        }
        BeanUtils.copyProperties(member,existingMemberDetails,"memberId");
        return memberRepository.saveAndFlush(existingMemberDetails);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void deleteMemberDetails(@PathVariable Long id){
        if(memberRepository.findByMemberId(id)==null){
            throw new GlobalNotFoundException("Member does not exist!");
        }
        memberRepository.deleteById(id);
    }
}
