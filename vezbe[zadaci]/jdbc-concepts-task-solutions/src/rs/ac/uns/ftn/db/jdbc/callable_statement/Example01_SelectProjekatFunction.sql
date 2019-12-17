-- run this statement in sqlDeveloper --
create or replace FUNCTION F_SEL_RadprojCnt(P_Spr int, P_brc int) RETURN int
IS 
    V_RadProjCnt int;
BEGIN
    SELECT count(*) INTO V_RadProjCnt
    FROM radproj rp
    WHERE rp.Spr = P_Spr and rp.brc > P_brc;

    RETURN V_RadProjCnt;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN NULL;
END F_SEL_RadprojCnt;