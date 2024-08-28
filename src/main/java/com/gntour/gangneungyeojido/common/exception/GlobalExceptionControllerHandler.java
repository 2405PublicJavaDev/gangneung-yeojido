package com.gntour.gangneungyeojido.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionControllerHandler {
    private static final String ERROR_PAGE_MSG = "msg";
    private static final String ERROR_PAGE = "common/error";
    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Model model, HttpServletRequest request) {
        log.error("handleMethodArgumentNotValidException", ex);
        if (isAjax(request)) {
            final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, ex.getBindingResult());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        model.addAttribute(ERROR_PAGE_MSG, ex.getMessage());
        return ERROR_PAGE;
    }

    /**
     * BusinessException 이 발생할 때 처리해야 할 코드
     * id가 중복된다, email 이 유효하지 않다 등
     * @param e business exception
     * @param model error page 에 전달할 것
     * @return String error page 경로
     */
    @ExceptionHandler(BusinessException.class)
    public Object handleBusinessException(BusinessException e, Model model, HttpServletRequest request) {
        log.error("handleBusinessException", e);
        if (isAjax(request)) {
            final ErrorResponse response = ErrorResponse.of(e.getErrorCode());
            return new ResponseEntity<>(response, e.getErrorCode().getStatus());
        }
        model.addAttribute(ERROR_PAGE_MSG, e.getMessage());
        return ERROR_PAGE;
    }

    /**
     * 그 외에 알지 못하는 오류가 있을 때
     */
    @ExceptionHandler(Throwable.class)
    protected Object handleException(Throwable e, Model model,HttpServletRequest request) {
        log.error("handleException", e);
        if(isAjax(request)) {
            final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        model.addAttribute(ERROR_PAGE_MSG, "심각한 서버 에러 발생 반드시 조치하세요");
        return ERROR_PAGE;
    }

    private boolean isAjax(HttpServletRequest request) {
        return "application/json".equals(request.getHeader("Content-Type"));
    }
}
