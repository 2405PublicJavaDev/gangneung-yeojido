package com.gntour.gangneungyeojido.domain.email.service;

public interface EmailValidService {
    int addOrUpdateValidCode(String email, String validCode);
    boolean isValidEmail(String email, String validCode);
}
