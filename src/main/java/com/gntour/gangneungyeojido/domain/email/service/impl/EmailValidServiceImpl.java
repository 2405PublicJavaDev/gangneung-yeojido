package com.gntour.gangneungyeojido.domain.email.service.impl;

import com.gntour.gangneungyeojido.domain.email.mapper.EmailValidMapper;
import com.gntour.gangneungyeojido.domain.email.service.EmailValidService;
import com.gntour.gangneungyeojido.domain.email.vo.EmailValid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailValidServiceImpl implements EmailValidService {
    private final EmailValidMapper emailValidMapper;

    @Override
    public int addOrUpdateValidCode(String email, String validCode) {
        EmailValid emailValid = emailValidMapper.selectOneByEmail(email);
        EmailValid updatedEmailValid = new EmailValid();
        updatedEmailValid.setEmail(email);
        updatedEmailValid.setValidCode(validCode);
        LocalDateTime tenMinutesLater = LocalDateTime.now().plusMinutes(10);
        updatedEmailValid.setExpired(Timestamp.from(tenMinutesLater.atZone(ZoneId.systemDefault()).toInstant()));
        if(emailValid == null) {
            return emailValidMapper.insertEmailValid(updatedEmailValid);
        } else {
            return emailValidMapper.updateEmailValid(updatedEmailValid);
        }
    }

    @Override
    public boolean isValidEmail(String email, String validCode) {
        EmailValid emailValid = emailValidMapper.selectOneByEmail(email);
        return emailValid != null &&
                emailValid.getValidCode().equals(validCode) &&
                isBeforeNow(emailValid.getExpired());
    }

    // 현재 시간과 expired 값을 비교하는 헬퍼 메서드
    private boolean isBeforeNow(Timestamp expired) {
        // 현재 시간 가져오기
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // 만료 시간이 현재 시간 이후인 경우 true 반환
        return expired != null && expired.after(now);
    }
}
