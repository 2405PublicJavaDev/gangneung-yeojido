package com.gntour.gangneungyeojido.domain.email.mapper;

import com.gntour.gangneungyeojido.domain.email.vo.EmailValid;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailValidMapper {
    int insertEmailValid(EmailValid emailValid);
    int updateEmailValid(EmailValid emailValid);
    EmailValid selectOneByEmail(String email);
}
