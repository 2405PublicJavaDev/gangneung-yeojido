package com.gntour.gangneungyeojido.domain.qna.mapper;

public interface QnAMapper {
    public void selectAllQnA();
    public void selectAllQnACountByMemeber();
    public void selectAllQnAAnswer();
    public void selectAllQnAAnswerCountByMember();
    public void insertQnA();
    public void insertQnAAnswer();
    public void deleteQnA();
    public void deleteQnAAnswer();

}
