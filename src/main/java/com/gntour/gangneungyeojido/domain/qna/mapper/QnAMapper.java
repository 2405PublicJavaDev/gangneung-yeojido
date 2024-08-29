package com.gntour.gangneungyeojido.domain.qna.mapper;

public interface QnAMapper {
    void selectAllQnA();
    void selectAllQnACount();
    void selectAllQnACountByMember();
    void selectAllQnAAnswer();
    void selectAllQnAAnswerCount();
    void selectAllQnAAnswerCountByMember();
    void insertQnA();
    void insertQnAFile();
    void insertQnAAnswer();
    void deleteQnA();
    void deleteQnAFile();
    void deleteQnAAnswer();

}
