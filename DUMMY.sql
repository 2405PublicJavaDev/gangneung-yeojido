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