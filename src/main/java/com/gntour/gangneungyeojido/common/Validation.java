package com.gntour.gangneungyeojido.common;

public class Validation {
    /**
     * TRAVEL NAME 은 빈칸이면 안된다.
     */
    public static final String TRAVEL_NAME_VALIDATION = "(.|\\s)*\\S(.|\\s)*";

    /**
     * ID 정규식
     */
    public static final String ID_VALIDATION = "^[A-Za-z0-9]{5,20}$";

    /**
     * PW 정규식
     */
    public static final String PW_VALIDATION = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$";

    /**
     * EMAIL 정규식
     */
    public static final String EMAIL_VALIDATION = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    /**
     * BIRTH DATE 정규식
     */
    public static final String BIRTHDATE_VALIDATION = "^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$";

    /**
     * PHONE 정규식
     */
    public static final String PHONE_VALIDATION = "^\\d{10,11}$";
}
