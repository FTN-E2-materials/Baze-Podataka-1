create table drzava (
    idd integer,
    nazivd varchar2(40) not null,
    constraint drzava_pk primary key (idd),
    constraint drzava_uk unique (nazivd)
);


create table mesto (
    idmes integer,
    nazivm varchar2(40),
    idd integer not null,
    constraint mesto_pk primary key (idmes),
    constraint mesto_fk foreign key (idd) references drzava (idd)
);


-- izmena definicje tabele

alter table pozoriste 
    add ( idmes integer);
-- 3.97
-- U tabelu pozoriste dodati obelezje idmes(identifikator mesta)
-- koje je strani kljuc propagiran iz tabele mesto.
-- Vazi ogranicenje refernacijalnog integriteta:
-- pozoriste[idmes] pravipodskup mesto[idmes]

alter table pozoriste
    add constraint poz_mesto_fk foreign key (idmes) references mesto (idmes) on delete cascade;

-- 3.98
-- Obezbediti da pri onosu nove torke u tabelu podela ne moze da se unese datum prestanka angazovanja glumca
-- na ulozi koji je stariji od datuma dodele uloge.


alter table podela
    add constraint podela_ch check ( datump > datumd);

-- 3.101
-- U tabelu drzava uneti drzave Srbiju i Crnu Goru

insert into drzava (idd,nazivd) values (1,'Srbija');
insert into drzava (idd,nazivd) values (2,'Crna Gora');

delete drzava
where nazivd = 'Crna Gora';

update drzava 
set nazivd = 'Velika Srbija' where nazivd = 'Srbija';

-- 3.102
-- U tabelu mesto dodati gradove Beograd i Novi Sad.

insert into mesto (idmes,nazivm,idd) values (1,'Beograd',1);
insert into mesto (idmes,nazivm,idd) values (2,'Novi Sad',1);

-- 3.103
-- U tabelu pozoriste uneti Sprsko narodno pozoriste.
-- Adresa pozorista je Pozoristni trg 1,Novi Sad,a adresa sajta je https://www.snp.org.rs

insert into pozoriste (idpoz,nazivpoz,adresapoz,sajt,idmes) 
values (10,'Sprsko narodno pozoriste','Pozoristni trg 1','https://www.snp.org.rs',2);

-- brisanje glumca Vanja Ejbus
delete glumac where imeg = 'Vanja' and prezg = 'Ejbus';


-- 3.105
-- Povecati platu svim glumcima za 20 procenata
update glumac
set plata=1.2*plata;

-- 3.106
-- Svim pozoristima koja imaju vrednost identifikatora od 1 do 4 dodati da su u Beogradu

update pozoriste
set idmes = 1 where idpoz between 1 and 4;

-- 3.107
-- Za izvodjenje predstave pod nazivom Otelo koja se prikazivala 5.03.2018 u 20h
-- dodati da je broj gledalaca bio 555

update prikazivanje set brojgled = 555
where datumpri = '05-mar-2018'  and vremepri ='20:00' and idpred in ( select idpred from predstava where nazivpred = 'Otelo');

-- 3.108
-- Kreirati pogled koji ce prikazivati maticni broj,ime i prezime svih gostujucih glumaca

create or replace view gostujuci_glumci (maticni_broj,ime,prezime) as
select mbg,imeg,prezg
from glumac
where status='g';

-- 3.82
-- Prikazati za svakog glumca kojem je dodeljena neka uloga,mbg,prezg,imeg,imepred,imeulog
-- i broj drugih glumaca kojima je dodeljena ista uloga.

with br_gl_uloge as
(--ovde cuvamo koliko svaka uloga ima glumaca
--voditi racuna posle da ce to uzeti ukupni zbir glumaca na toj ulozi,pa cemo morati oduzeti naseg glumca
--jer kaze broj drugih glumaca
    select imeulo,count(mbg) as br_glumaca
    from podela
    group by imeulo
)
select gl.mbg,gl.prezg,gl.imeg,pr.nazivpred,pl.imeulo,(bgu.br_glumaca - 1) as br_drugih_glumaca
from podela pl,glumac gl,predstava pr,br_gl_uloge bgu
where pl.mbg = gl.mbg and pl.idpred = pr.idpred and pl.imeulo = bgu.imeulo;

-- 3.83.
-- Prikazati za svakog glumca kom je dodeljena uloga,mbg,prezg,imeg kao i imepred,imeulo
-- i udeo u ukupnom honoraru koji se izdvaja za dodeljenu ulogu za sve njene glumce 
-- Udeo izraziti u procentima zaokruzeno na dve decimale.

with podelainfo as
(--izdvajam koliki je ukupni honorar na svakoj predstavi za svaku ulogu
    select po.idpred,po.imeulo,sum(nvl(po.honorar,0)) as suma_honorara
    from podela po
    group by po.idpred,po.imeulo
    order by po.idpred
)
select gl.mbg,gl.prezg,gl.imeg,pr.nazivpred,pl.imeulo,(round(nvl(pl.honorar*100/pi.suma_honorara,0),2) || ' posto' ) as udeo
from glumac gl,podela pl,predstava pr,podelainfo pi
where gl.mbg = pl.mbg and pl.idpred = pr.idpred and pl.imeulo = pi.imeulo;

-- 3.84
-- Prikazati maticni broj,ime,prezime i platu glumca ciji je honorar za neku ulogu 
-- veci od prosecnog honorara za tu ulogu
with avg_hon as
(--prosecan honorar za svaku ulogu
    select po.imeulo,avg(nvl(po.honorar,0)) as prosecni_honorar_po_ulozi
    from podela po
    group by po.imeulo
)
select distinct gl.mbg,gl.imeg,gl.prezg,gl.plata
from glumac gl,podela pl,avg_hon ah
where gl.mbg = pl.mbg and pl.imeulo = ah.imeulo and pl.honorar > ah.prosecni_honorar_po_ulozi;

-- 3.85.
-- Prikazati maticni broj,ime,prezime i platu glumca ciji je honorar za neku ulogu 
-- veci od prosecnog honorara svih uloga

with podelainfo as
(--nadjem prosecan honorar svih uloga
    select round(avg(nvl(po.honorar,0)),1) prosek
    from podela po
)
select distinct gl.mbg,gl.imeg,gl.prezg,gl.plata
from glumac gl,podela po,podelainfo pi
where gl.mbg = po.mbg and nvl(po.honorar,0) > pi.prosek;

-- 3.86
-- Prikazati koliki je ukupni honorar svih glumaca na svim njihovim predstavama.
-- Uzeti u obzir samo glumce koji glume i u predstavama koje se ne prikazuju u njihovom maticnom pozoristu

--nadjem glumce koji glume i u svom i tudjem maticnom pozoristu
--ne uzeti one koji glume samo u svom pozoristu
with glumacinfo as
(   --nadjem prvo u kom sve pozoristu glumi koji glumac
    select distinct gl.mbg,gl.imeg,gl.prezg,pr.idpoz,poz.nazivpoz,nvl(to_char(gl.maticnopoz),'nema MatPoz') as "idMatPoz"
    from glumac gl,podela po,prikazivanje pr,pozoriste poz
    where gl.mbg = po.mbg and po.idpred = pr.idpred and pr.idpoz = poz.idpoz
    order by gl.mbg
), glumacPredstave as
(--nadjem predstave koje se prikazuju u kom pozoristu
    select distinct pr.idpoz,pr.idpred
    from prikazivanje pr
    order by pr.idpoz
)
select *
from glumacPredstave;











