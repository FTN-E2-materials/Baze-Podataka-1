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





