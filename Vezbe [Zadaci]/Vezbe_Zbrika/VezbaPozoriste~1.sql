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

-- 3.40
-- Prikazati maticni broj,ime,prezime i platu glumaca koji zaradjuju vise od glumca
-- sa maticnim brojem 5

select g1.mbg,g1.imeg,g1.prezg,g1.plata,g2.plata as "PLATA RADNIKA 5"
from glumac g1,glumac g2
where g1.plata > g2.plata and g2.mbg = 5;

-- 3.41
-- Prikazati nazive scena i pozorista kojima pripadaju,
-- za sve scene ciji broj sedista je u intervalu pluts/minus 20% od broja sedista koje ima
-- scena Joakim Vujic pozorista Knjazevsko-sprski teatar Kragujevac

select * 
from scena s,pozoriste p,scena s1,pozoriste p1
where s.idpoz = p.idpoz and s.brojsed >= s1.brojsed*0.8 and s.brojsed <= s1.brojsed*1.2
and s1.nazivsce = 'Scena Joakim Vujic'
and s1.idpoz = p1.idpoz
and p1.nazivpoz = 'Knjazevsko-srpski teatar Kragujevac';



-- 3.42
-- Prikazati identifikator predstave,broj izvodjenja,ukupan kao i prosecan broj gledalaca na svim izvodjenjima predstave
-- za svaku predstavu.

select idpred,count(rbr) as "broj izvodjenja",sum(brojgled) as "ukupan br. gledalaca",avg(brojgled) as "prosecan br. gledalaca" 
from prikazivanje
group by idpred;

-- 3.43
-- Prikazati identifikator predstave,broj izvodjenja,ukupan kao i prosecan broj gledalaca na svim izodjenjima predstave
-- ,za sve predstava ciji je broj izvodjenja veci od 4

select idpred,count(rbr) as "broj izvodjenja",sum(brojgled) as "ukupan br. gledalaca",avg(brojgled) as "prosecan br. gledalaca" 
from prikazivanje
group by idpred
having count(rbr) > 4;


-- 3.44
-- Prikazati naziv predstave,broj izvodjenja i prosecan broj gledalaca na svim izvodjenjima predstave

select pred.nazivpred,count(rbr) "br_izvodjenja",avg(brojgled) "prosecan_br_gledalaca"
from prikazivanje prik,predstava pred
where prik.idpred = pred.idpred
group by pred.idpred,pred.nazivpred;

-- 3.45.
-- Za svaku predstavu prikazati minimalni i maksimalni broj gledalaca u svim prikazivanjima

select idpred,max(brojgled),min(brojgled)
from prikazivanje
group by idpred;

-- 3.46
-- Prikazati ukupan broj uloga kao i ukupni honorar svih gostujucih glumaca koji su trenutno angazovani u nekoj ulozi u pozoristu

select gl.mbg,gl.imeg,gl.prezg,count(po.imeulo) "Ukupno uloga" ,sum(po.honorar) "Ukupni honorar"
from glumac gl,podela po
where gl.status='g' and gl.mbg = po.mbg and po.datump is null
group by gl.mbg,gl.imeg,gl.prezg;
--datump je datum prestanka angazovanja glumca na ulozi

-- 3.47
-- Prikazti za svakog glumca broj uloga u kojima ucestvoje

select g.imeg,g.prezg,count(p.imeulo) broj_uloga
from glumac g left outer join podela p
on g.mbg = p.mbg
group by g.mbg,g.imeg,g.prezg;













