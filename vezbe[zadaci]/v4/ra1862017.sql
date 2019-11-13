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















