package com.gntour.gangneungyeojido.domain.qna.service;

public interface QnAService {
    void getAllQnA();
    void getAllQnAByMember();
    void getAllQnAAnswerByMember();
    void addQnA();
    void addQnAAnswer();
    void removeQnA();
    void removeQnAAnswer();

}
