INSERT INTO MEMBER(MEMBER_ID, PASSWORD, NAME, BIRTH_DATE, EMAIL, PHONE, STATUS, ROLE)
VALUES('admin', 'admin', 'admin', SYSTIMESTAMP, 'admin@admin.com', '01011112222', 'NORMAL', 'ADMIN');
BEGIN
    FOR i IN 1..205 LOOP
        INSERT INTO REQ_MARK_ADD (
            REQ_MARK_ADD_NO,
            TRAVEL_NAME,
            ADDRESS,
            PHONE,
            USE_TIME,
            PARK_FEE,
            ENTRY_FEE,
            USE_FEE,
            INTRODUCE,
            ACCEPTABLE_STATUS,
            SITE_URL,
            IMAGE_URL,
            MEMBER_ID,
            REG_DATE,
            UPDATE_DATE
        ) VALUES (
            i,  -- Auto-incrementing primary key
            'Travel Name ' || i,  -- Travel name placeholder
            'Address ' || i,  -- Address placeholder
            '010-' || TO_CHAR(FLOOR(DBMS_RANDOM.VALUE(1000, 9999))) || '-' || TO_CHAR(FLOOR(DBMS_RANDOM.VALUE(1000, 9999))),  -- Random phone number
            '09:00 AM - 06:00 PM',  -- Use time placeholder
            TO_CHAR(FLOOR(DBMS_RANDOM.VALUE(0, 100))) || ' USD',  -- Random park fee
            TO_CHAR(FLOOR(DBMS_RANDOM.VALUE(0, 50))) || ' USD',  -- Random entry fee
            TO_CHAR(FLOOR(DBMS_RANDOM.VALUE(0, 150))) || ' USD',  -- Random use fee
            'This is a sample introduction for travel site ' || i,  -- Introduction placeholder
            CASE WHEN MOD(i, 2) = 0 THEN 'Y' ELSE 'N' END,  -- Alternating acceptable status
            'http://www.example.com/site' || i,  -- Site URL placeholder
            'http://www.example.com/image' || i,  -- Image URL placeholder
            'admin',  -- Random member ID
            SYSTIMESTAMP,  -- Registration date (current timestamp)
            SYSTIMESTAMP  -- Update date (current timestamp)
        );
    END LOOP;
    COMMIT;
END;
/

DECLARE
    v_min_travel_no NUMBER;
v_max_travel_no NUMBER;
v_min_review_no NUMBER;
v_max_review_no NUMBER;
v_random_travel_no NUMBER;
v_random_review_no NUMBER;
v_score_list SYS.ODCINUMBERLIST := SYS.ODCINUMBERLIST(1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0);
BEGIN
-- TRAVEL_INFO에서 TRAVEL_NO의 최소값과 최대값을 가져옵니다.
SELECT MIN(TRAVEL_NO), MAX(TRAVEL_NO)
INTO v_min_travel_no, v_max_travel_no
FROM TRAVEL_INFO;

-- 최소, 최대값이 NULL인 경우 오류 발생 방지
IF v_min_travel_no IS NULL OR v_max_travel_no IS NULL THEN
        RAISE_APPLICATION_ERROR(-20001, 'TRAVEL_INFO 테이블에 데이터가 없습니다.');
END IF;

    -- REVIEW 테이블에 500개의 레코드를 삽입합니다.
FOR i IN 1..500 LOOP
        -- 무작위로 TRAVEL_NO 선택
        v_random_travel_no := TRUNC(DBMS_RANDOM.VALUE(v_min_travel_no, v_max_travel_no + 1));

        -- 선택된 TRAVEL_NO가 유효한지 확인
BEGIN
SELECT TRAVEL_NO
INTO v_random_travel_no
FROM TRAVEL_INFO
WHERE TRAVEL_NO = v_random_travel_no;

-- 유효하면 삽입
INSERT INTO REVIEW (
    REVIEW_NO,
    SCORE,
    REVIEW_CONTENT,
    PARENT_REVIEW_NO,
    TRAVEL_NO,
    MEMBER_ID,
    REG_DATE,
    UPDATE_DATE
) VALUES (
             i,
             v_score_list(TRUNC(DBMS_RANDOM.VALUE(1, 10))),  -- SCORE는 1.0 ~ 5.0 사이에서 랜덤 선택
             'Review content ' || i,  -- REVIEW_CONTENT는 임의의 텍스트
             NULL,  -- PARENT_REVIEW_NO는 NULL
             v_random_travel_no,  -- 유효한 TRAVEL_NO
             'admin',
             SYSTIMESTAMP,
             SYSTIMESTAMP
         );
EXCEPTION
            WHEN NO_DATA_FOUND THEN
                -- 유효하지 않은 TRAVEL_NO인 경우 다음 반복으로 건너뜁니다.
                CONTINUE;
END;
END LOOP;

    -- REVIEW 테이블에서 REVIEW_NO의 최소값과 최대값을 가져옵니다.
SELECT MIN(REVIEW_NO), MAX(REVIEW_NO)
INTO v_min_review_no, v_max_review_no
FROM REVIEW;

-- REVIEW_COMPLAIN 테이블에 300개의 레코드를 삽입합니다.
FOR i IN 1..300 LOOP
        -- 무작위로 REVIEW_NO 선택
        v_random_review_no := TRUNC(DBMS_RANDOM.VALUE(v_min_review_no, v_max_review_no + 1));

        -- 선택된 REVIEW_NO가 유효한지 확인
BEGIN
SELECT REVIEW_NO
INTO v_random_review_no
FROM REVIEW
WHERE REVIEW_NO = v_random_review_no;

-- 유효하면 삽입
INSERT INTO REVIEW_COMPLAIN (
    COMPLAIN_NO,
    CATEGORY,
    REVIEW_NO,
    MEMBER_ID,
    REG_DATE,
    UPDATE_DATE
) VALUES (
             i,
             'Category ' || i,  -- CATEGORY는 임의의 텍스트
             v_random_review_no,  -- 유효한 REVIEW_NO
             'admin',
             SYSTIMESTAMP,
             SYSTIMESTAMP
         );
EXCEPTION
            WHEN NO_DATA_FOUND THEN
                -- 유효하지 않은 REVIEW_NO인 경우 다음 반복으로 건너뜁니다.
                CONTINUE;
END;
END LOOP;

    -- 커밋
COMMIT;
EXCEPTION
    WHEN OTHERS THEN
ROLLBACK;
DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

DECLARE
v_score_list SYS.ODCINUMBERLIST := SYS.ODCINUMBERLIST(1.0, 2.0, 3.0, 4.0, 5.0);
BEGIN
    -- REVIEW 테이블에 500개의 레코드를 삽입합니다.
FOR i IN 1..100 LOOP

        -- 선택된 TRAVEL_NO가 유효한지 확인
INSERT INTO REVIEW (
    REVIEW_NO,
    SCORE,
    REVIEW_CONTENT,
    PARENT_REVIEW_NO,
    TRAVEL_NO,
    MEMBER_ID,
    REG_DATE,
    UPDATE_DATE
) VALUES (
             SEQ_REVIEW_NO.NEXTVAL,
             v_score_list(TRUNC(DBMS_RANDOM.VALUE(1, 5))),  -- SCORE는 1.0 ~ 5.0 사이에서 랜덤 선택
             'Review content ' || i,  -- REVIEW_CONTENT는 임의의 텍스트
             904,
             352,  -- 유효한 TRAVEL_NO
             'admin',
             SYSTIMESTAMP,
             SYSTIMESTAMP
         );
END LOOP;
COMMIT;
END;
/
DECLARE
v_score        NUMBER(3,1);
    v_member_id    VARCHAR2(100);
    v_review_no    NUMBER;
    v_p_r_no       NUMBER := NULL; -- PARENT_REVIEW_NO, 기본값은 NULL
    v_travel_no    NUMBER := 352;  -- TRAVEL_NO 기본값
    v_member_prefix VARCHAR2(10) := 'khuser';
    v_member_suffix NUMBER(3);
    v_score_index  NUMBER := 1;    -- SCORE 순환 인덱스
BEGIN
FOR i IN 1..100 LOOP
        -- SCORE를 1.0에서 5.0까지 순차적으로 설정
        v_score := ROUND(v_score_index, 1);
        v_score_index := v_score_index + 1;
        IF v_score_index > 5 THEN
            v_score_index := 1;
END IF;

        -- MEMBER_ID를 순차적으로 설정 (khuser001 ~ khuser100)
        v_member_suffix := i;
        v_member_id := v_member_prefix || LPAD(v_member_suffix, 3, '0');

        -- SEQUENCE를 사용하여 REVIEW_NO를 설정
SELECT SEQ_REVIEW_NO.NEXTVAL INTO v_review_no FROM DUAL;

-- REVIEW 테이블에 데이터 삽입
INSERT INTO REVIEW (
    REVIEW_NO,
    SCORE,
    REVIEW_CONTENT,
    PARENT_REVIEW_NO,
    TRAVEL_NO,
    MEMBER_ID,
    REG_DATE,
    UPDATE_DATE
) VALUES (
             v_review_no,
             v_score,
             'Sample review content', -- REVIEW_CONTENT 기본값
             v_p_r_no,
             v_travel_no,
             v_member_id,
             SYSTIMESTAMP,
             SYSTIMESTAMP
         );
END LOOP;

COMMIT;
END;
/

DECLARE
v_score        NUMBER(3,1);
    v_member_id    VARCHAR2(100) := 'khuser002'; -- MEMBER_ID 고정
    v_review_no    NUMBER;
    v_p_r_no       NUMBER := NULL; -- PARENT_REVIEW_NO, 기본값은 NULL
    v_travel_no    NUMBER;       -- TRAVEL_NO, 순서대로 설정
    v_member_prefix VARCHAR2(10) := 'khuser';
    v_score_index  NUMBER := 1;    -- SCORE 순환 인덱스
BEGIN
FOR i IN 148..168 LOOP
        -- 현재 TRAVEL_NO 설정
        v_travel_no := i;

        -- SCORE를 1.0에서 5.0까지 순차적으로 설정
        v_score := ROUND(v_score_index, 1);
        v_score_index := v_score_index + 1;
        IF v_score_index > 5 THEN
            v_score_index := 1;
END IF;

        -- SEQUENCE를 사용하여 REVIEW_NO를 설정
SELECT SEQ_REVIEW_NO.NEXTVAL INTO v_review_no FROM DUAL;

-- REVIEW 테이블에 데이터 삽입
INSERT INTO REVIEW (
    REVIEW_NO,
    SCORE,
    REVIEW_CONTENT,
    PARENT_REVIEW_NO,
    TRAVEL_NO,
    MEMBER_ID,
    REG_DATE,
    UPDATE_DATE
) VALUES (
             v_review_no,
             v_score,
             'Sample review content', -- REVIEW_CONTENT 기본값
             v_p_r_no,
             v_travel_no,
             v_member_id,
             SYSTIMESTAMP,
             SYSTIMESTAMP
         );
END LOOP;

COMMIT;
END;
/
