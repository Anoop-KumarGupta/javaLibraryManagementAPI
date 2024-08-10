package com.example.lms.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="member")
@Table(name="Member")
public class Member {
    @Id
    @Column(name="member_id")
    private int memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_age")
    private String memberAge;

    @Column(name = "book_issued")
    private boolean bookIssued;

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private Set<Issued> issued;
}
