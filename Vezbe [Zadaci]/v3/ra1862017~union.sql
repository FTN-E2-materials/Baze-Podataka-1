--Unija (UNION)
--• Izlistati mbr, ime, prz radnika koji rade na
--projektu sa šifrom 20 ili im je plata ve?a od
--prose?ne. (unija)

select mbr,ime,prz from radnik
where mbr in ( select mbr from radproj where spr=20)
union
select mbr,ime,prz from radnik
where plt > ( select avg(plt) from radnik);

--ili preko joina
select distinct r1.mbr,r1.ime,r1.prz
from radnik r1 left outer join radproj rp on r1.mbr = rp.mbr 
where (rp.spr = 20 or r1.plt > ( select avg(plt) from radnik));

--Unija (UNION ALL)
--• Izlistati mbr, ime, prz radnika koji rade na
--projektu sa šifrom 20 ili im je plata ve?a od
--prose?ne. (unija)













