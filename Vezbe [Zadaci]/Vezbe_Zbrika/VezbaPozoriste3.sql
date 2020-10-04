-- 3.61
-- Prikazati nazive pozorista na kojima se izvodila predstava pod nazivom Otelo

select distinct pz.nazivpoz,pr.nazivpred
from prikazivanje prik,pozoriste pz,predstava pr
where prik.idpoz = pz.idpoz and prik.idpred = pr.idpred and pr.nazivpred='Otelo';

-- njihov nacin,preko ugnjezdenog upita
select nazivpoz
from pozoriste
where idpoz in
(
    select idpoz 
    from prikazivanje pr,predstava p
    where pr.idpred = p.idpred and p.nazivpred='Otelo'
);

-- 3.62.
-- Prikazati nazive predstava u kojima glumi barem jedan glumac
-- koji glumi i u predstavi sa sifrom 40

with glumac_na_40 as
(
--dobijem ko sve glumi na predstavi 40
    select po.idpred,pr.nazivpred,g2.mbg,g2.imeg,g2.prezg
    from podela po,glumac g2,predstava pr
    where po.mbg = g2.mbg and po.idpred = pr.idpred and po.idpred =40
),glumci_info as -- info koji daje na kojoj predstavi jos oni glume(tj koji je id te predstave)
(-- i vidim gde ti glumci jos glume tj na kojim predstavama
    select gl4.mbg,gl4.imeg,gl4.prezg,pl.idpred
    from glumac_na_40 gl4,podela pl
    where gl4.mbg = pl.mbg
)--i sad samo dodam naziv te predstave
select distinct p.nazivpred
from glumci_info gi,predstava p
where gi.idpred = p.idpred;


-- ili preko ugnjezdenog nezavisnog upita

select nazivpred
from predstava
where idpred in (select idpred from podela where mbg in( select mbg from podela where idpred=40));

-- 3.63.
-- Prikazati nazive predstava koji imaju najveci prosecan broj gledalaca

select prik.idpred,pr.nazivpred,round(avg(prik.brojgled),2) prosecno_gledalaca
from prikazivanje prik,predstava pr
where prik.idpred = pr.idpred
group by prik.idpred,pr.nazivpred
having round(avg(prik.brojgled),2) >= all ( select round(avg( prika.brojgled),2) from prikazivanje prika group by prika.idpred) ;

-- 3.64.
-- Prikazati nazive predstava koje imaju najmanji ukupni broj gledalaca

select pr.nazivpred,sum(prik.brojgled) ukupno_gledalaca
from prikazivanje prik,predstava pr
where prik.idpred = pr.idpred
group by prik.idpred,pr.nazivpred
having sum(prik.brojgled) <= all (select sum(prika.brojgled) from prikazivanje prika group by prika.idpred);

-- 3.65
-- Prikazati glumce ciji prosecni honorar po ulozi je veci od prosecnog honorara na svi predstavama
select g.mbg,g.imeg,g.prezg,avg(p.honorar)
from glumac g,podela p
where g.mbg = p.mbg
group by g.mbg,g.imeg,g.prezg
having avg(p.honorar) >= (select avg(po.honorar) from podela po);


-- 3.66
-- Prikazati glumce sa najvecim prosecnim honorarom po ulozi

select pl.mbg,gl.imeg,gl.prezg,round(avg(nvl(pl.honorar,0)),2) prosecni_honorar
from podela pl,glumac gl
where pl.mbg = gl.mbg
group by pl.mbg,gl.imeg,gl.prezg
having round(avg(nvl(pl.honorar,0)),2) >= all ( select nvl(avg(p.honorar),0) from podela p group by p.mbg )
order by pl.mbg;


-- 3.67
-- Za svaku predstavu prikazati naziv i datum prikazivanja kada je imala najveci broj posetilaca
with predstava_info as
(
    select prik.idpred,p.nazivpred,prik.datumpri,prik.brojgled
    from prikazivanje prik,predstava p
    where prik.idpred = p.idpred
    group by prik.idpred,p.nazivpred,prik.datumpri,prik.brojgled
),max_gl_info as
(
    select idpred,max(brojgled) max_gledalaca
    from predstava_info
    group by idpred
    order by idpred
)
select nazivpred,datumpri,brojgled
from predstava_info pi,max_gl_info mgi
where pi.idpred = mgi.idpred and pi.brojgled = mgi.max_gledalaca;


-- ili preko zavisnog ugnjezdenog upita
select prik.idpred,p.nazivpred,prik.datumpri,prik.brojgled
from prikazivanje prik,predstava p
where prik.idpred = p.idpred
group by prik.idpred,p.nazivpred,prik.datumpri,prik.brojgled
having prik.brojgled = ( select max(pru.brojgled) from prikazivanje pru where pru.idpred = prik.idpred);

-- 3.68
-- Prikazati najstarijeg glumca

--najstariji je onaj sa sto manjim datumr
select najstariji.mbg,najstariji.imeg,najstariji.prezg,najstariji.datumr
from glumac najstariji
where not exists ( select * from glumac g where g.datumr < najstariji.datumr) ;

-- 3.69
-- Prikazati glumce koji,za sada,ne ucestvuju ni u jednoj predstavi.

select *
from glumac g
where not exists ( select * from podela p where p.mbg = g.mbg);

-- 3.70
-- Za svakog glumca priakzati naziv predstave i uloge u predstavi,za koju je dobio najveci honorar
with max_hon as
(-- za koju predstavu je dobio najveci honorar
    select po.mbg,max(nvl(po.honorar,0)) as max_honorar
    from podela po
    group by po.mbg
)
select g.imeg,g.prezg,p.imeulo,pr.nazivpred,mh.max_honorar
from max_hon mh,podela p,predstava pr,glumac g
where mh.mbg = p.mbg and mh.max_honorar = p.honorar and p.idpred = pr.idpred and mh.mbg = g.mbg;
-- sad to spojim opet s podelom,preko honorar i mbg i dobijem o kojoj je predstavi je rec(tj njen id )
-- i onda preko tog id-a spojim sa predstavom da bi dobio naziv te predstave
-- i jos samo dodam glumca da bi dobio info o njegovom imenu i prezimenu

-- 3.71
-- Prikazati glumce ciji je honorar za ulogu u predstavi
-- veci od prosecnog honorara za tu predstavu
-- uvecan za 20%

with prosecan_hon as
(--nadjem koliki je prosecan honorar za svaku predstavu
    select po.idpred,round(avg(po.honorar),2) as prosek  --dodao round(radi lepseg izgleda samo )
    from podela po
    group by po.idpred
)--prikazem glumce koji imaju honorar veci od tog proseka
select gl.mbg,gl.imeg,gl.prezg,pl.honorar
from prosecan_hon ph,podela pl,glumac gl
where ph.idpred = pl.idpred and pl.honorar > ph.prosek *1.2 and pl.mbg = gl.mbg;

-- 3.72
-- Prikazati pozorista cija najveca scena moze da primi vise od 500 gledalaca

with scena_info as
(
    select sc.idpoz,max(sc.brojsed) najvise_gled
    from scena sc
    group by sc.idpoz
)
select p.nazivpoz,si.najvise_gled
from scena_info si,pozoriste p
where si.najvise_gled > 500 and si.idpoz = p.idpoz;






