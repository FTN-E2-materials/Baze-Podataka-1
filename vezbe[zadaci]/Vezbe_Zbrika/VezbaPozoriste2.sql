-- Ugnjezdeni upiti

-- 3.48
-- Izlistati sve uloge koje se pojavljuju u predstavama sa identifikatorima 10,20 ili 30

select *
from uloga
where idpred in (10,20,30);

-- 3.49
-- Izlistati muske uloge koje se pojavljuju u predstavama sa identifikatorom 10,20 ili 30

select *
from uloga 
where pol = 'm' and idpred in(10,20,30);

-- 3.50
-- Prikazati imena i prezimena svih glumaca kooji se zovu Milena ili Nikola
select *
from glumac
where imeg in ('Milena','Nikola');

-- 3.51 
-- Prikazati imena i prezimena svih glumaca koji se ne zovu Milena ili Nikola
select *
from glumac
where imeg not in ('Milena','Nikola');

-- 3.52.
-- Prikazati imena,prezimena i platu svih glumaca koji se zovu Bogdan ili Nikola

select *
from glumac
where imeg = any ('Bogdan','Nikola');
-- any je tacan ako se zadovolji makar jedan od vrednosti

-- 3.53.
-- Prikazati imena,prezimena i platu svih glumaca koji ucestvuju u predstavama sa identifikatorom 30,40 ili 50

select g.imeg,g.prezg,g.plata
from glumac g,podela p
where g.mbg = p.mbg and p.idpred = any (30,40,50);

-- 3.54
-- Prikazati imena,prezimena i platu svih glumaca koji se ne zovu Milena ili Nikola
select imeg,prezg,plata
from glumac
where imeg != ALL ('Milena','Nikola');
-- ALL je tacan npr ako je razlicito(!=) od SVIH VREDNOSTI 

-- 3.55.
-- Prikazati imena,prezimena i platu svih glumaca koji ne ucestvuju u predstavama sa identifikatorom 10,20,40 ili 50

select g.imeg,g.prezg,g.plata
from glumac g,podela p
where g.mbg = p.mbg and p.idpred != ALL (10,20,40,50);

-- 3.56.
-- Izlistati u rastucem redosledu plate 
-- ime,prezime i platu glumaca koji imaju platu vecu od prosecne plate svih glumaca

select gl.imeg,gl.prezg,gl.plata
from glumac gl
where gl.plata > ( select avg(plata) from glumac)
order by gl.plata asc;

-- 3.57.
-- Prikazati glumce kojima nije dodeljena nijedna uloga
select mbg,imeg,prezg
from glumac
where mbg not in ( select distinct mbg from podela);
-- (select mbg from podela) da nam tabelu gde imamo one glumce koji imaju ulogu

-- 3.58
-- Prikazati sve glumce koji ucestvuju u predstavi sa identifikatorom 40

select imeg,prezg
from glumac
where mbg in ( select mbg from podela where idpred=40) ;
-- a moglo je i mbg = ANY (..);

-- 3.59
-- Prikazati sve glumce koji ucestvuju u predstavi sa sifrom 10,a ne ucestvuju u predstavi sa sifrom 30
select *
from glumac
where mbg in ( select mbg from podela where idpred = 10)
and mbg not in (select mbg from podela where idpred=30);

-- 3.60
-- prikazati predstave koje se najduze prikazuju tj. koje su imale najstariju godinu prve premijere

select *
from predstava
where godinapre <= ALL( select godinapre from predstava);
--ili
select *
from predstava
where godinapre = (select min(godinapre) from predstava);

-- 3.61
-- Prikazati nazive pozorista na kojima se izvodila predstava pod nazivom otelo

select nazivpoz 
from pozoriste
where idpoz = ANY ( select prik.idpoz
from predstava pred,prikazivanje prik
where nazivpred = 'Otelo' and pred.idpred = prik.idpred);

-- 3.62
-- Prikazati nazive predstava u kojima glumi barem jedan glumac koji glumi i u predstavi sa sifrom 40



