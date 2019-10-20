--ZADACI PRATE SLAJDOVE SA VEZBI
--select distinct ime from radnik;
--selektujem sva imena iz radnika [ bez duplikata]

--WHERE
--select mbr,ime,prz,plt
--from radnik
--where plt>25000;

-- NUL VREDNOST
--Izlistati mbr, ime, prz radnika koji nemaju sefa
--select mbr,ime,prz,plt
--from radnik
--where sef is not null;

--BETWEEN tj [ ]
--• Izlistati mbr, ime, prz radnika ?ija je plata
--izme?u 20000 i 24000 dinara.

--select mbr,ime,prz,plt
--from radnik
--where plt between 20000 and 24000;

--select mbr,ime,prz,plt
--from radnik
--where plt >= 20000 and plt <= 35000;

--LIKE
--operator % uzima bilo koji znak 
--Izlistati mbr, ime, prz radnika ?ije prezime
--po?inje na slovo M. 

--select mbr,ime,prz
--from radnik
--where prz like 'M%';

--select mbr,ime,prz
--from radnik
--where prz like '%m%' or prz like '%M%';

--IN
--Izlistati mati?ne brojeve radnika koji rade
--na projektima sa šifrom 10, 20 ili 30. 
--
--select distinct mbr
--from radproj
--where spr in(10,20,30);
--distinct kako ne bi bez veze ispisalo mbr radnika koji radi na vise projekata



--Izlistati mati?ne brojeve radnika koji rade
--na projektu sa šifrom 10 ili rade 2, 4, ili 6
--sati.
--select mbr,spr,brc
--from radproj
--where spr = 10 or brc in(2,4,6);

--NOT IN
-- Izlistati mati?ne brojeve radnika koji se ne zovu Ana ili Sanja.
--
--select mbr,ime
--from radnik
--where ime not in ('Ana','Sanja');

--ORDER
--SELECT Mbr, Prz, Ime
--FROM Radnik
--ORDER BY Ime ASC;

--SELECT Mbr, Prz, Ime,plt
--FROM Radnik
--ORDER BY 2, 3, Plt;
--sortira prvo po 2[po prezimenu] a onda u okviru istih prezimena po 3 tj imenu

--Prikazati mati?ne brojeve, spojena
--(konkatenirana) imena i prezimena
--radnika, kao i plate, uve?ane za 17%.
--CONCAT fja || kao + u javi
--SELECT Mbr,
--Ime || ' ' || Prz "Ime i prezime",
--Plt * 1.17 Plata
--FROM Radnik; 

-- Prikazati radnike ?ije prezime sadri ime.
--Na primer Marko Markovi?, ili Djordje
--Karadjordjevic
-- Funkcije UPPER, LOWER
--1.sve ide na mala slova tj. Marko prebacimo u marko i Markovic u markovic
--2.where markovic like xxxxxxxxxmarkoxxxxxx
--3.karadjordjevic
--
--SELECT * 
--from radnik 
--where LOWER(prz) LIKE  LOWER(ime) ;
-- where marko deo xxxxxxmarkoxxxxx
--ANY
-- Prikazati mati?ne brojeve radnika, imena i
--prezimena i platu radnika koji se zovu
--Pera ili Moma.
--SELECT Mbr, Ime, Prz, Plt
--FROM Radnik
--WHERE Ime = ANY ('Pera', 'Moma');
--ALL
--SELECT Mbr, Ime, Prz, Plt
--FROM Radnik
--WHERE Ime !=ALL ('Pera', 'Moma');
--Funkcija NVL(izraz, konstanta)
--• Prikazati mati?ne brojeve radnika, kao i
--plate, uve?ane za godišnju premiju.
--Ukoliko za nekog radnika vrednost premije
--ne postoji, smatrati da ona iznosi 0.
SELECT Mbr, Plt + NVL(Pre, 0)
FROM Radnik;


