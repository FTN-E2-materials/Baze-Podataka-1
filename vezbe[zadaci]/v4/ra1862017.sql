--Pogled
--• Napraviti pogled koji ce za sve radnike
--prikazati samo njihova imena, prezimena i
--platu.

create or replace view
plate_radnika(Ime,Prezime,Plata) as
select ime,prz,plt
from radnik;


--• Prikazati radnike ?ije prezime na po?etku
--sadrži prva 3 slova imena, na primer:
--Petar Petric

select *
from radnik
where lower(prz) like substr(lower(ime),0,3) || '%';


--• Prikazati imena i prezimena radnika tako
--da se sva imena koja imaju poslednje
--slovo 'a', prikazuju bez poslednjeg slova.


select ime,trim(trailing 'a' from ime)
from radnik;


--• Svim radnicima promeniti ime tako da
--poslednje slovo bude uve?ano.
--• Primer: AnA -> AnA, Marko -> MarkO

select ime,substr(ime,1,length(ime)-1) || upper(substr(ime,length(ime),1))
from radnik;


--• Za svakog radnika prikazati ime, prz, i projekte na kojima
--radi. Ako ne radi ni na jednom projektu, napisati ‘Ne radi
--na projektu’. Imena radnika prikazati velikim slovima, a
--prezimena malim.


select upper(r.ime),lower(r.prz),nvl(to_char(rp.spr),'Ne radi na projektu') as Projekat
from radnik r left outer join radproj rp on r.mbr = rp.mbr;


--• Prikazati za svakog radnika angažovanog na projektu
--mbr, prz, ime, spr i broj drugih radnika koji su
--angažovani na istom projektu
select r.mbr, r.ime, r.prz, rp1.spr, count(rp2.mbr)-1 ostali
from radnik r, radproj rp1, radproj rp2
where r.mbr=rp1.mbr and rp1.spr=rp2.spr
group by r.mbr, r.ime, r.prz, rp1.spr;

with rad_na_proj as ( select rp.spr, count(rp.mbr) as br_rad from radproj rp group by rp.spr)
select r.mbr,r.prz,r.ime,rp.spr,rnp.br_rad-1 ostali
from radnik r,radproj rp,rad_na_proj rnp
where r.mbr = rp.mbr and rp.spr = rnp.spr;


--• Prikazati za svakog radnika angažovanog na projektu mbr, prz, ime, spr i
--udeo u ukupnom broju ?asova rada na tom projektu 
--(zaokruženo na dve decimale)

--udeo je njegovo angazovanje na tom projektu / ukupno angazovanje svih radnika na tom projektu
with angz_na_proj as ( select rp.spr,sum(rp.brc) ukupno_ang from radproj rp group by rp.spr)
select r.mbr,r.prz,r.ime,rp.spr,round(rp.brc/ang.ukupno_ang,2) as Udeo
from radnik r,radproj rp,angz_na_proj ang
where r.mbr = rp.mbr and rp.spr = ang.spr ;

--• Prikazati mbr, ime, prz, plt radnika ?iji je broj sati angažovanja na nekom projektu ve?i od
--prose?nog broja sati angažovanja na tom projektu

with avg_ang as ( select rp.spr,avg(rp.brc) as prosecno_angazovanje from radproj rp group by rp.spr) 
select distinct r.mbr,r.ime,r.prz,r.plt
from radnik r,radproj rp1,avg_ang ang
where r.mbr = rp1.mbr and rp1.spr = ang.spr and rp1.brc > ang.prosecno_angazovanje;

--njihovo resenje na slajdu 180 mi nije bas najasnije
--slajd 180
--with projinfo as (
--select spr, avg(brc) prosek
--from radproj group by spr)
--select distinct r.mbr, r.ime, r.prz, r.plt
--from radnik r, radproj rp, projinfo pi
--where r.mbr=rp.mbr and rp.spr=pi.spr
--group by r.mbr, r.ime, r.prz, r.plt, pi.spr
--having avg(rp.brc)>(select prosek from projinfo pi2
--where pi2.spr=pi.spr);

--• Prikazati mbr, ime, prz, plt radnika ?iji je broj sati angažovanja na nekom projektu 
--ve?i od prose?nog angažovanja na svim projektima
-- ja sam radio preko vise with-a
with projinfo as 
(
    select rp.spr,avg(rp.brc) as prosek from radproj rp group by rp.spr
),
prosecno_ang as
(
    --prosecno_ang ce imati info o prosecnom angazovanju na svim projektima
    select round(avg(pi.prosek),3) as prosecno_na_svim_pr
    from projinfo pi
)
select distinct r.mbr,r.ime,r.prz,r.plt
from radnik r,radproj rp,prosecno_ang pan
where r.mbr = rp.mbr and rp.brc > pan.prosecno_na_svim_pr;


-- njihovo resenje,slajd 181
with projinfo as ( select spr, avg(brc) pros from radproj group by spr)
select distinct r.mbr, r.ime, r.prz, r.plt
from radnik r, radproj rp, projinfo pi
where r.mbr=rp.mbr and rp.spr=pi.spr
group by r.mbr, r.ime, r.prz, r.plt, pi.spr
having avg(rp.brc)>(select avg(pros) from projinfo);















