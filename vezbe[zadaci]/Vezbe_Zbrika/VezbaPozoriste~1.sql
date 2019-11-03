-- zad 3.1.
-- Prikazati citav sadrzaj tabele Predstava

select * 
from predstava;

-- 3.2
-- Prikazati za sva pozoriste,nazive i adrese njihovih sajtova.

select nazivpoz,sajt
from pozoriste;

-- 3.30
-- Prikazati za sve scene na kojima se izvode predstave naziv scene,naziv pozorista
-- kojem scena pripada,kao i broj gledalaca koje scena moze da primi

select nazivsce,nazivpoz,brojsed
from scena,pozoriste
where scena.idpoz = pozoriste.idpoz;

-- 3.31
-- Prikazati za sve predstave njihove nazive,kao i nazive svih uloga u predstavi
select nazivpred,imeulo
from predstava,uloga
where predstava.idpred=uloga.idpred;

-- 3.32
-- Prikazati za sve glumce nazive uloga,kao i honorar koji dobijaju za tu ulogu

select u.imeulo,g.imeg,g.prezg,p.honorar
from glumac g,uloga u,podela p
where g.mbg = p.mbg and p.imeulo = u.imeulo and p.idpred=u.idpred;

-- 3.33.
-- Za sve glumce koji imaju maticno pozoriste prikazati ime,prezime i naziv maticnog pozorista

select g.imeg,g.prezg,p.nazivpoz
from glumac g,pozoriste p
where g.maticnopoz is not null and g.maticnopoz = p.idpoz;

--a moglo je i samo g.maticnopoz = p.idpoz

-- 3.34
-- Prikazati za sve predstave njihove nazive,nazive svih uloga u predstavi
-- kao i glumce kojima su te uloge dodeljene

select p.nazivpred "NAZIV PREDSTAVE",u.imeulo "NAZIV ULOGE",g.imeg || ' ' || g.prezg "Ime i prezime glumca"
from predstava p,uloga u,podela po,glumac g
where p.idpred = u.idpred and u.idpred=po.idpred and u.imeulo = po.imeulo and po.mbg = g.mbg
order by p.nazivpred;

-- 3.35
-- Prikazati nazive scena i pozorista na kojima se izvodila predstava pod nazivom Otelo

--select distinct sc.nazivsce "NAZIV SCENE",po.nazivpoz "NAZIV POZORISTA",pred.nazivpred "PREDSTAVA"
--from scena sc,pozoriste po,prikazivanje prik,predstava pred
--where sc.idpoz = po.idpoz and prik.idpoz=po.idpoz and prik.idpred = pred.idpred and pred.nazivpred = 'Otelo';

--ovo bi bilo greska jer bi ovako izbacili pozoriste(sastoji se vise od scena) u kom je na bar jednoj sceni
--glumljena predstava Otelo,a mi zelimo samo scene gde je glumljeno Otelo i ime tog pozorista

select distinct prik.nazivsce,poz.nazivpoz
from prikazivanje prik,predstava pred,pozoriste poz
where prik.idpred = pred.idpred and pred.nazivpred= 'Otelo' and prik.idpoz = poz.idpoz;


-- 3.36
-- Za svakog glumca prikazati naziv njegovog maticnog pozorista
-- Ukljuciti i glumce koji imaju status slobodnog umetnika tj. nemaju maticno pozoriste

select g.imeg,g.prezg,p.nazivpoz
from glumac g left outer join pozoriste p
on g.maticnopoz = p.idpoz;

select g.imeg,g.prezg,p.nazivpoz
from pozoriste p right outer join glumac g 
on  p.idpoz =g.maticnopoz ;

-- 3.37
-- Za svakog glumca prikazati naziv njegovog maticnog pozorista.
-- Ukoliko nema maticno pozoriste u prikazu rezultata treba da pise 'Slobodan umetnik'

select g.imeg || ' ' || g.prezg as "Glumac" ,NVL(p.nazivpoz,'Slobodan umetnik') as "Naziv pozorista"
from glumac g left outer join pozoriste p
on g.maticnopoz = p.idpoz;

-- 3.38
-- Prikazati za sve glumce naziv njihovog maticnog pozorista.
-- Ukoliko nemaju maticno pozoriste treba da pise 'Slobodan umetnik'. Takodje
-- , prikazati sva pozorista bez obzira da li imaju glumce koji gostuju u predstavama

select g.imeg || ' ' || g.prezg as "Glumac" ,NVL(p.nazivpoz,'Slobodan umetnik') as "Naziv pozorista"
from glumac g full outer join pozoriste p
on g.maticnopoz = p.idpoz;

-- 3.39
-- Prikazati sve muske uloge za predstavu pod nazivom Otelo.
-- Ukoliko se uloge podeljene prikazati prezimena glumaca koji ih igraju.
-- Ukoliko ima uloga koje nisu podeljene treba u prikazu rezultata da pise 'Jos nepodeljena'

select NVL(g.prezg,'Jos nepodeljena'), u.imeulo
from uloga u left outer join podela p on u.idpred = p.idpred and u.imeulo = p.imeulo
left outer join glumac g on p.mbg = g.mbg 
left outer join predstava p on u.idpred = p.idpred
where u.pol = 'm' and p.nazivpred='Otelo';







