--COUNT fja
--
--
--• Koliko ima šefova? 

select distinct count(sef) as "Broj Sefova" from radnik;

select count( distinct sef) from radnik;

--Funkcije max i min
--• Prikazati minimalnu i maksimalnu platu
--radnika. 

select min(plt) as min_plata,max(plt) as max_plata from radnik;

--Funkcija sum
--• Prikazati broj radnika i ukupnu mese?nu
--platu svih radnika. 

select count(mbr) as broj_radnika,sum(plt) as mesecna_plata from radnik;

--Funkcija avg
--• Prikazati broj radnika, prose?nu platu i
--ukupnu godišnju platu svih radnika. 

select count(mbr) as broj_radnika,avg(plt) as prosecna_plata,sum(12*plt) as godisnja from radnik;

--• Prikazati ukupnu premiju svih radnika ?iji
--je mati?ni broj ve?i od 100

select sum(pre) as premije
from radnik
where mbr < 100;

--• Prikazati broj radnika, prose?nu platu zaokruzenu na 2 decimale i
--ukupnu godišnju platu svih radnika. 
select count(mbr) as broj_radnika,round(avg(plt),2) as prosecna_plata,sum(12*plt) as godisnja from radnik;

--Select naredba u listi tabela
SELECT count(plt) FROM (SELECT MBR,IME,PLT FROM radnik where plt<10000);

--ROWNUM
--Prikazati 10 radnika koji imaju najve?u platu,
--sortiranih po plati u opadaju?em redosledu
-- ovo nije dobro,zbog toga treba da prvo uzmemo tih 10 i onda tu uradimo sort 
select mbr,plt,rownum 
from radnik
where rownum <=10
order by plt desc ;

select mbr,plt,rownum from (select * from radnik order by plt desc) where rownum<=10;


--Zadatak avg, round – select
--naveden u listi kolona
--• Prikazati za svakog radnika red koji sadrži
--njegovu platu, prose?nu platu i apsolutnu
--(ABS) razliku prose?ne plate i njegove
--plate.

select plt "plata",(select avg(plt) from radnik) prosecna,abs( (select avg(plt) from radnik) - plt) apsolutna from radnik;

--GROUP BY - Uvod
--• SELECT mbr, spr FROM radproj where
--mbr < 40;

SELECT mbr, spr 
FROM radproj 
where
mbr < 40;

SELECT mbr,sum(brc) 
FROM radproj
group by mbr
order by sum(brc) desc;

--
--GROUP BY
--• Prikazati koliko radnika radi na svakom
--projektu i koliko je ukupno angažovanje na
--tom projektu? 

select spr,count(mbr),sum(brc)
from radproj
group by spr
order by sum(brc) desc;

--HAVING
--• Izlistati mbr radnika koji rade na više od dva
--projekta, pored mbr-a, prikazati i broj projekata
--na kojima radnici rade. 

select mbr,count(spr)
from radproj
group by mbr
having count(spr)>2;

--•Prikazati radnike sa mbr-om manjim od 40 koji rade na tacno dva projekta

select mbr,count(spr)
from radproj
group by mbr
having count(spr)=2;

--Nezavisni ugnježdeni upiti
--• Izlistati u rastu?em redosledu plate mbr,
--ime, prz i plt radnika koji imaju platu ve?u
--od prose?ne. 

select mbr, ime, prz, plt
from radnik
where plt>(select avg(plt) from radnik)
order by plt asc;


--Nezavisni ugnježdeni upiti
--• Izlistati imena i prezimena radnika koji
--rade na projektu sa šifrom 30

select mbr,ime,prz from radnik
where mbr in (select mbr from radproj where spr = 30);

--• Izlistati mbr, ime, prz radnika koji rade na
--projektu sa šifrom 10, a ne rade na projektu sa
--šifrom 30. 

select mbr,ime,prz from radnik
where mbr in(select mbr from radproj where spr=10) and mbr not in( select mbr from radproj where spr=30);

--• Izlistati ime, prz i god najstarijeg radnika. 

select ime,prz,god
from radnik
where god = (select min(god) from radnik);

--Spajanje tabela
--• Prikazati mbr, prz, ime, plt i brc
--angažovanja svih radnika koji rade na
--projektu sa šifrom 10. 

select radnik.mbr,prz,ime,plt,brc
from radnik,radproj
where radnik.mbr = radproj.mbr and spr =10;

--• Prikazati mbr, ime, prz i plt radnika koji su
--rukovodioci projekata.

select distinct radnik.mbr,radnik.ime,radnik.prz,radnik.plt
from radnik,projekat
where radnik.mbr = projekat.ruk;

--•Izlistati imena, prezimena svih radnika
--osim rukovodioca projekta sa šifrom 10

select distinct r.mbr,ime,prz,p.spr
from radnik r,projekat p
where p.spr = 10 and r.mbr != p.ruk ;

select ime,prz
from radnik
where mbr != (select ruk from projekat where spr=10);

--
--Primer KOLOKVIJUM-a
--• Izlistati nazive projekata na kojima radi bar
--jedan radnik koji radi i na projektu sa
--šifrom 60.

select projekat.nap from projekat where spr in (select spr from radproj where mbr in (select mbr from radproj where spr=60)); 
--selektujemo radnike koji rade na projektu sa sifrom 60 i onda selektujemo sve projekte na kojima rade ti radnici i onda
--uzmemo nazive projekata na kojima rade ti radnici

select * from radproj where spr=20;
select spr from radproj where mbr in (select mbr from radproj where spr=60);

--
--•Prikazati imena i prezimena rukovodilaca
--projekata i broj projekata kojima rukovode

select prz, ime, count(spr)
from radnik r, projekat p
where ruk=mbr
group by mbr, prz, ime;

select distinct ime,prz,count(spr)
from radnik,projekat
where mbr=ruk
group by ime,prz;

--• Prikazati za svakog radnika mbr, prz, ime,
--ukupan broj projekata i ukupno
--angažovanje na projektima na kojima radi. 

select radnik.mbr,prz,ime,count(spr) as "UKUPAN BROJ PROJEKATA",SUM(brc) as "UKUPNO ANGAZOVANJE"
from radnik,radproj
where radnik.mbr=radproj.mbr
group by radnik.mbr,prz,ime
order by sum(brc) desc; --dodao samo order da bude opadajuci po ukupnom angazovanju


--• Prikazati imena i prezimena rukovodilaca
--projekata i broj projekata na kojima rade. 

select distinct ime,prz,count(spr) as "BROJ PROJEKATA NA KOJIM RADI"
from radnik,projekat
where mbr=ruk
group by mbr,ime,prz
order by count(spr) desc;

--• Izlistati nazive projekata na kojima se radi vise od 15 casova

select nap,sum(brc)
from projekat,radproj
where projekat.spr=radproj.spr
group by nap
having sum(brc)>15;







