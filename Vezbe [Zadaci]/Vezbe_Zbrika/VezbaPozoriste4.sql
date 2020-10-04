-- 3.108
-- Kreirati pogled koji ce prikazivati maticni broj,ime i prezime svih gostujucih glumaca

--create or replace view gostujuci_glumci(*) as
create or replace view gostujuci_glumci (maticni_broj,ime,prezime) as
select mbg,imeg,prezg
from glumac
where status = 'g'
order by mbg;

-- 3.109
-- Kreirati pogled koji ce prikazivati za sve predstave datume predvidjene za igranje predstava

create or replace view predstava_info ( naziv_predstave,datum_prikazivanja) as
select pr.nazivpred,prik.datumpri
from predstava pr,prikazivanje prik
where pr.idpred = prik.idpred
order by pr.nazivpred,prik.datumpri ;        --cisto da bi soritrao datum prikazivanja unutar svake predstave,radi lepseg pregleda

select *
from predstava_info;

-- 3.110
-- Kreirati pogled koji ce za sve glumce prikazivati maticni broj i osnovna mesecna primanja(mesecnu platu + dodatk na platu)

create or replace view osnovna_primanja_glumaca (maticni_broj,ime,prezime,osnovna_primanja)as 
select mbg,(plata + nvl(dodatak,0)) as mesecna_primanja
from glumac;

select *
from osnovna_primanja_glumaca;

-- 3.111
-- Kreirati pogled koji ce prikazivati ukupan broj uloga i ukupni honorar glumaca za sve uloge na kojima su trenutno angazovani
create or replace view gl_brul_ukhon (maticni_broj,ukupno_uloga,ukupni_honorar) as
select gl.mbg,count(po.imeulo) as ukupno_uloga, sum(nvl(po.honorar,0)) as ukupni_honorar
from glumac gl,podela po
where gl.mbg = po.mbg and po.datump is null
group by gl.mbg;

select *
from gl_brul_ukhon;

-- 3.112
-- Kreirati poged koji ce prikazivati maticni broj,ime,prezime i ukupna mesecna primanja svakog glumca
-- (osnovna primanja + honorar)

create or replace view  UkuPrim_glumaca (maticni_broj,ime,prezime,ukupna_mesecna_primanja) as
select gl.mbg,gl.imeg,gl.prezg,opg.osnovna_primanja+nvl(ukh.ukupni_honorar,0)
from glumac gl left outer join osnovna_primanja_glumaca opg on gl.mbg = opg.maticni_broj
left outer join gl_brul_ukhon ukh on opg.maticni_broj = ukh.maticni_broj;

select *
from ukuprim_glumaca;

-- 3.113
-- Prikazati ukupno mesecno zaduzenje pozorista za isplatu svim glumcima

select sum(ukupna_mesecna_primanja) zaduzenje
from ukuprim_glumaca;













