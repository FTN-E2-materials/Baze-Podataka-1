--• INSERT – dodavanje nove torke

insert into Radnik (mbr, ime, prz, plt,
sef, god) values (201, 'Ana', 'Savic',
30000, null, '18-aug-71');
insert into Projekat (spr, nap, ruk)
values (90, 'P1', 201);
insert into RadProj (mbr, spr, brc)
values (201, 90, 5);


insert into Radnik (mbr,ime,prz,plt,sef,god) values(202,'Vladislav','Maksimovic',1000000,202,'03-jul-98');
insert into radproj (mbr,spr,brc) values (202,10,8);
delete radnik where mbr=202;
update radnik set pre = 8000 where mbr = 202;

select *
from radnik r,radproj rp
where r.mbr = rp.mbr and ime = 'Vladislav';

--faze_projekta({Spr , Sfp, Rukfp, Nafp,Datp}, {Spr+ Sfp})

CREATE TABLE faze_projekta(
	 spr integer not null,
     sfp integer not null,
     rukfp integer,
     nafp varchar2(30) unique,
     datp date,     	 
	 CONSTRAINT faze_projekta_PK PRIMARY KEY (spr,sfp),
	 CONSTRAINT faze_projekta_FK FOREIGN KEY (spr) REFERENCES projekat (spr),
     CONSTRAINT faze_projekta_FK2 FOREIGN KEY (rukfp) REFERENCES radnik (mbr) 
);
--• U tabelu faze_projekta dodati atribut:
--Datz - datum završetka faze projekta.
--• Datz ne sme biti manji od Datp

alter table faze_projekta
    add (datz date);
    
alter table faze_projekta
    add constraint faze_projekta_CH check (datz >= datp);
--• U tabelu faze_projekta dodati bar dve faze
--za jedan projekat i jednu za drugi projekat


insert into faze_projekta(spr,sfp,rukfp,nafp,datp,datz) values (10,11,50,'f1','13-jan-97','13-jan-98');
insert into faze_projekta(spr,sfp,rukfp,nafp,datp,datz) values (20,12,100,'f2','15-jan-97','10-jan-98');


--• Za svaki projekat prikazati sifru projekta,
--naziv projekta, ime i prezime rukovodioca
--projekta, prezime njegovog šefa, nazive
--faza projekta, imena i prezimena
--rukovodioca faza projekta. Ako projekat
--nije podeljen u faze napisati: nema faze.

select pr.spr,pr.nap,r.ime,r.prz,r2.prz,nvl(fp.nafp,'nema faze') as faze_pr,fp.rukfp,r3.ime,r3.prz
from projekat pr left outer join radnik r on pr.ruk = r.mbr
left outer join radnik r2 on r.sef = r2.mbr
left outer join faze_projekta fp on pr.spr = fp.spr
left outer join radnik r3 on fp.rukfp = r3.mbr
order by spr desc;
--where pr.ruk = r.mbr and r.sef = r2.mbr and pr.spr = fp.spr and fp.rukfp = r3.mbr;

drop table faze_projekta;





