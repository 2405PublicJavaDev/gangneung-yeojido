package com.gntour.gangneungyeojido.domain.qna.service;

public interface QnAService {
    public void getAllQnA();
    public void getAllQnAByMember();
    public void getAllQnAAnswerByMember();
    public void addQnA();
    public void addQnAAnswer();
    public void removeQnA();
    public void removeQnAAnswer();

}
