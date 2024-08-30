package com.gntour.gangneungyeojido.domain.member.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class Member {
    private String memberId;
    private String password;
    private String name;
    private Timestamp birthDate;
    private String email;
    private String phone;
    private String status;
    private String deleteYn;
    private String role;
    private Timestamp regDate;
    private Timestamp updateDate;
}
