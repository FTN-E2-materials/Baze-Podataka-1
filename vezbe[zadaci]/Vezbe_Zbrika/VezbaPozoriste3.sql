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








