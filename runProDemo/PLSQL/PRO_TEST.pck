CREATE OR REPLACE PACKAGE PRO_TEST IS

  -- Author  : ADMINISTRATOR
  -- Created : 2017/11/12 16:57:44
  -- Purpose : 
  
  TYPE RECORD_SET IS REF CURSOR;
  --PROCEDURE DEMO
  PROCEDURE PRO_QUERY_LIST(P_USER_NAME    IN  VARCHAR,
                           P_BEGIN_ROWNUM IN NUMBER,
                           P_END_ROWNUM   IN NUMBER,
                           P_RECORD_COUNT OUT NUMBER,
                           P_FLAG         OUT NUMBER,
                           P_MSG          OUT VARCHAR2,
                           P_RECORDSET     OUT RECORD_SET
                            );
  
   --FUNCTION DEMO
   FUNCTION FUN_QUERY_LIST(
                           P_USER_NAME    IN  VARCHAR
                          ) RETURN NUMBER;
                          
     --新增和更新
   PROCEDURE PRO_ADD_USER_BATCH(P_ID               IN VARCHAR2,
                                ADD_USER_INFO      IN USER_NEW_ADD_TAB,
                                P_FLAG             OUT NUMBER,
                                P_MSG              OUT VARCHAR2,
                                P_RECORDSET        OUT RECORD_SET);
END PRO_TEST;
/
CREATE OR REPLACE PACKAGE BODY PRO_TEST IS

   --PROCEDURE DEMO
   PROCEDURE PRO_QUERY_LIST(P_USER_NAME    IN  VARCHAR,
                            P_BEGIN_ROWNUM IN NUMBER,
                            P_END_ROWNUM   IN NUMBER,
                            P_RECORD_COUNT OUT NUMBER,
                            P_FLAG         OUT NUMBER,
                            P_MSG          OUT VARCHAR2,
                            P_RECORDSET    OUT RECORD_SET
                            )IS
     
     V_SQL      VARCHAR2(500);
     V_WHERE    VARCHAR2(500);
   BEGIN
     V_SQL := 'SELECT UT.*,
                      ROW_NUMBER() OVER(PARTITION BY 1 ORDER BY UT.ID)RW ';
     V_WHERE := 'FROM USER_TEST UT 
                WHERE 1=1';
     IF P_USER_NAME IS NOT NULL THEN
       V_WHERE := V_WHERE || ' And  UT.USER_NAME LIKE ''%'||TRIM(P_USER_NAME)||'%''';
     END IF;
     EXECUTE IMMEDIATE 'SELECT COUNT(1) FROM ('||V_SQL||V_WHERE||')'
             INTO P_RECORD_COUNT;         
     IF P_RECORD_COUNT = 0 THEN
       OPEN P_RECORDSET FOR 
            SELECT NULL FROM DUAL WHERE 1=2;
     ELSE
       OPEN P_RECORDSET FOR 
            'SELECT * FROM ('||V_SQL||V_WHERE||') WHERE RW>='||P_BEGIN_ROWNUM||' AND RW<='||P_END_ROWNUM; 
     END IF;
     P_FLAG := 0;
     P_MSG := 'OK';
   
   EXCEPTION
     WHEN OTHERS THEN
       P_FLAG := 101;
       P_MSG := '数据查询失败：'||SQLERRM;
       --DBMS_OUTPUT.PUT_LINE(SUBSTR(SQLERRM,200));
       OPEN P_RECORDSET FOR
        SELECT 'FAIL' STATUS FROM DUAL;
   END PRO_QUERY_LIST;
   
   --FUNCTION DEMO
   FUNCTION FUN_QUERY_LIST(
                           P_USER_NAME    IN  VARCHAR
                          ) RETURN NUMBER IS
     V_COUNT NUMBER DEFAULT 0;
   BEGIN
      SELECT COUNT(1)
        INTO V_COUNT
        FROM USER_TEST UT
       WHERE UT.USER_NAME LIKE '%'||P_USER_NAME||'%';
    
      IF V_COUNT > 0 THEN
        return 1;
      ELSE
        return 0;
      END IF;
   END FUN_QUERY_LIST;
   
  --新增和更新
  PROCEDURE PRO_ADD_USER_BATCH(P_ID               IN VARCHAR2,
                               ADD_USER_INFO      IN USER_NEW_ADD_TAB,
                               P_FLAG             OUT NUMBER,
                               P_MSG              OUT VARCHAR2,
                               P_RECORDSET        OUT RECORD_SET) IS
    V_ADD_USER_INFO   USER_NEW_ADD_TAB;
    V_COUNT           NUMBER DEFAULT 0;
    V_FLAG            NUMBER DEFAULT 0;
    V_MSG             VARCHAR2(4000);
    E_NO_ENTITY EXCEPTION;
    E_SAVE_ERR EXCEPTION;
  BEGIN
    V_FLAG            := 0;
    V_MSG             := '';
    V_ADD_USER_INFO := ADD_USER_INFO;
    --判断TAB中的数据
    IF V_ADD_USER_INFO.COUNT = 0 THEN
      raise E_NO_ENTITY;
    END IF;
  
    FOR I IN 1 .. V_ADD_USER_INFO.COUNT LOOP
      IF V_ADD_USER_INFO(I).O_ID IS NOT NULL THEN
        SELECT COUNT(1)
          INTO V_COUNT
          FROM USER_TEST T
         WHERE T.ID = V_ADD_USER_INFO(I).O_ID;
      
        IF V_COUNT = 0 THEN
          V_FLAG := V_FLAG + 1;
          V_MSG  := '该ID的员工不存,请核对信息!';
        END IF;
      END IF;
    
      IF V_FLAG = 0 THEN
        IF V_ADD_USER_INFO(I).O_ID IS NOT NULL THEN
          UPDATE USER_TEST UT
             SET UT.ID            = V_ADD_USER_INFO(I).O_ID,
                 UT.USER_NAME     = V_ADD_USER_INFO(I).O_USER_NAME,
                 UT.AGE           = V_ADD_USER_INFO(I).O_AGE,
                 UT.SEX           = V_ADD_USER_INFO(I).O_SEX
           WHERE 1 = 1
             AND UT.ID = V_ADD_USER_INFO(I).O_ID;
             P_MSG  := '数据更新成功！';
        ELSE
          INSERT INTO USER_TEST
            (ID,
             USER_NAME,
             AGE,
             SEX)
          VALUES
            (SYS_GUID(),
             V_ADD_USER_INFO(I).O_USER_NAME,
             V_ADD_USER_INFO(I).O_AGE,
             V_ADD_USER_INFO(I).O_SEX);
             P_MSG  := '数据新增成功！';
        END IF;
      END IF;
    END LOOP;
    IF V_FLAG <> 0 THEN
      RAISE E_SAVE_ERR;
    ELSE
      COMMIT;
    END IF;
  
    OPEN P_RECORDSET FOR
      SELECT 'SUCCESS' FROM DUAL;
  Exception
    WHEN E_NO_ENTITY THEN
      ROLLBACK;
      P_FLAG := 101;
      P_MSG  := '没有数据实体需要维护！';
      OPEN P_RECORDSET FOR
        SELECT 'FAIL' FROM DUAL;
    
    WHEN E_SAVE_ERR THEN
      ROLLBACK;
      P_FLAG := 101;
      P_MSG  := '新增人员数据维护失败：' || V_MSG;
      OPEN P_RECORDSET FOR
        SELECT 'FAIL' FROM DUAL;
    
    WHEN OTHERS THEN
      ROLLBACK;
      P_FLAG := 101;
      P_MSG  := '维护人员信息异常：' || SQLERRM;
      OPEN P_RECORDSET FOR
        SELECT 'FAIL' FROM DUAL;
  END PRO_ADD_USER_BATCH;
END PRO_TEST;
/
