--• Izlistati nazive projekata na kojima se
--ukupno radi više od 15 ?asova.
select nap,sum(brc) as "BRC NA PROJEKTU",radproj.spr
from projekat,radproj
where projekat.spr = radproj.spr
group by radproj.spr,nap
having sum(brc)>15;

--• Izlistati šifre i nazive projekata na kojima
--radi više od dva radnika. 

--nadjem odredjen projekat i vidim koliko radnika radi na njemu

select p.spr,p.nap,count(mbr) as "BROJ RADNIKA NA PROJEKTU"
from projekat p,radproj rp
where p.spr = rp.spr
group by rp.spr,p.nap,p.spr
having count(mbr) > 2;

--• Izlistati nazive i šifre projekata na kojima je
--prose?no angažovanje ve?e od prose?nog
--angažovanja na svim projektima.

select p.nap,p.spr,avg(brc) as "PROSECNO ANGAZOVANJE"
from projekat p,radproj rp
where p.spr = rp.spr
group by rp.spr,p.nap,p.spr 
having avg(brc) > (select avg(brc) from radproj);

--• Izlistati nazive i šifre projekata sa najve?im
--prose?nim angažovanjem.

--najvece prosecno angazovanje je vece od svih angazovanja

select p.nap,p.spr,avg(brc) as "PROSECNO ANGAZOVANJE"
from projekat p,radproj rp
where p.spr=rp.spr
group by rp.spr,p.nap,p.spr
having avg(brc) >= all(select avg(brc) from radproj group by spr);

--Upit sa višestrukom upotrebom
--iste tabele

--• Prikazati mbr, ime, prz, plt radnika koji
--zara?uju više od radnika sa mati?nim
--brojem 20

select r1.mbr,r1.ime,r1.prz,r1.plt,r1.mbr
from radnik r1,radnik r2
where r2.mbr=20 and r1.plt > r2.plt;


--• Prikazati imena, prezimena i plate radnika
--koji zara?uju bar 1000 dinara manje od
--rukovodioca projekta na kom radnik radi. 

--• Prikazati imena, prezimena i plate radnika
--koji zara?uju koliko i rukovodioc projekta na kom radnik radi. 

--na kom projektu radnik radi ?
--select r1.ime,r1.prz,r1.plt,rp.spr as "SIFRA PROJEKTA"
--from radnik r1,radproj rp
--where r1.mbr = rp.mbr;

--ko je rukovodilac na tom projektu
--select *
--from radnik r1,radproj rp,projekat p
--where r1.mbr = rp.mbr and rp.spr = p.spr;

--sad imamo ruk,i sad tog trebamo naci u radniku da vidimo koliku platu ima i reci da uzimamo onog ko ima jednaku platu 
select *
from radnik r1,radproj rp,projekat p,radnik r2
where r1.mbr = rp.mbr and rp.spr = p.spr and p.ruk = r2.mbr and r2.plt = r1.plt;

--• Prikazati imena, prezimena i plate radnika
--koji zara?uju bar 1000 dinara manje od
--rukovodioca projekta na kom radnik radi. 

--na kom projektu radnik radi ?
--select r1.ime,r1.prz,r1.plt,rp.spr as "SIFRA PROJEKTA"
--from radnik r1,radproj rp
--where r1.mbr = rp.mbr;

--ko je rukovodilac na tom projektu
--select *
--from radnik r1,radproj rp,projekat p
--where r1.mbr = rp.mbr and rp.spr = p.spr;

--sad imamo ruk,i sad tog trebamo naci u radniku da vidimo koliku platu ima
--select r1.ime,r1.prz,r1.plt
--from radnik r1,radproj rp,projekat p,radnik r2
--where r1.mbr = rp.mbr and rp.spr = p.spr and p.ruk = r2.mbr and r1.plt + 1000 < r2.plt;

--posto ispisuje vise puta istog radnika,dodajemo distinct
select distinct r1.ime,r1.prz,r1.plt
from radnik r1,radproj rp,projekat p,radnik r2
where r1.mbr = rp.mbr and rp.spr = p.spr and p.ruk = r2.mbr and r1.plt + 1000 < r2.plt;

--• Prikazati mbr, ime, prz, plt radnika ?iji je broj sati
--angažovanja na nekom projektu ve?i od
--prose?nog broja sati angažovanja na tom
--projektu.

select distinct r.mbr, ime, prz, plt, brc
from radnik r, radproj rp1
where r.mbr=rp1.mbr and
rp1.brc>(select avg(brc) from radproj rp2
where rp2.spr=rp1.spr);

--EXISTS
--• Ko je najstariji radnik? (exist)

select ime,prz,god
from radnik r
where not exists (select mbr from radnik r1 where r1.god < r.god);

--• Izlistati mbr, ime, prz radnika koji ne rade na
--projektu sa šifrom 10. (ne postoji radnik sa
--projekta 10 koji je jednak traženom radniku) 


select mbr,ime,prz
from radnik r
where not exists(select * from radproj rp where r.mbr = rp.mbr and rp.spr=10);


--• Izlistati radnike koji ne rade ni na jednom projektu. (ne
--postoji projekat na kom rade) 

select * 
from radnik r
where not exists ( select * from radproj rp where rp.mbr =r.mbr);

--• Izlistati radnike koji nisu rukovodioci projekata. (ne
--postoji projekat kojim rukovodi taj radnik)

select *
from radnik r
where not exists (  select * from projekat p where p.ruk = r.mbr);

--• Ko je najmla?i rukovodilac projekata?

select distinct mbr,ime,prz,god
from radnik r,projekat p
where r.mbr = p.ruk and not exists( select * from radnik r2,projekat p2 where r2.god > r.god and r2.mbr = p2.ruk);


--Unija (UNION)
--• Izlistati mbr, ime, prz radnika koji rade na
--projektu sa šifrom 20 ili im je plata ve?a od
--prose?ne. (unija)
--unija ide uvek izmedju dva selecta

select mbr, ime, prz 
from radnik
where mbr in (select mbr from radproj where spr=20) 
union
select mbr, ime, prz 
from radnik
where plt>(select avg(plt) from radnik);

--Unija (UNION ALL)
--• Izlistati mbr, ime, prz radnika koji rade na
--projektu sa šifrom 20 ili im je plata ve?a od
--prose?ne. (unija)
select mbr, ime, prz from radnik
where mbr in
(select mbr from radproj where spr=20)
union all
select mbr, ime, prz from radnik
where plt>(select avg(plt) from radnik);

--Presek (INTERSECT)
--• Izlistati mbr, ime, prz radnika ?ije prezime
--po?inje na slovo M ili slovo R i mbr, ime,
--prz radnika ?ije prezime po?inje na slovo
--M ili slovo P.

select mbr, ime, prz from radnik
where prz like 'M%' or prz like 'R%'
INTERSECT
select mbr, ime, prz from radnik
where prz like 'M%' or prz like 'P%';

--Razlika (MINUS)
--• Izlistati mbr, ime, prz radnika ?ije prezime
--po?inje na slovo M ili slovo R i mbr, ime,
--prz radnika ?ije prezime po?inje na slovo
--M ili slovo P.
select mbr, ime, prz from radnik
where prz like 'M%' or prz like 'R%'
MINUS
select mbr, ime, prz from radnik
where prz like 'M%' or prz like 'P%';


--Prirodno spajanje (NATURAL)
--• Prikazati ime i prz radnika koji rade na
--projektu sa šifrom 30.
select ime, prz
from radnik natural join radproj
where spr=30;

--Unutrašnje spajanje (INNER)
--• Prikazati ime i prz radnika koji rade na
--projektu sa šifrom 30.
select ime, prz
from radnik r inner join radproj rp
on r.mbr=rp.mbr
where spr=30;

--Spoljno spajanje (LEFT
--OUTER)
--• Prikazati mbr, ime i prz radnika i šifre projekata
--na kojima rade. Prikazati, tako?e, iste podatke i
--za radnike koji ne rade ni na jednom projektu, pri
--?emu za šifru projekta treba, u tom slu?aju,
--prikazati nedostaju?u vrednost.
select r.mbr,ime, prz, spr
from radnik r left outer join radproj rp
on r.mbr=rp.mbr;

--Spoljno spajanje (RIGHT
--OUTER)
--• Prikazati nazive svih projekata i mbr radnika koji
--rade na njima. Ukoliko na projektu ne radi ni
--jedan radnik ispisati nulu umesto mati?nog
--broja.

select nvl(rp.mbr, 0) "Mbr radnika", nap
from radproj rp right outer join projekat p
on rp.spr=p.spr;

