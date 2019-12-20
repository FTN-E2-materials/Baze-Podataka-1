CREATE TABLE Predstava(
	 idpred integer,
	 nazivpred varchar2(60) NOT NULL,
	 trajanje integer,
     	 godinapre integer,
	 CONSTRAINT Predstava_PK PRIMARY KEY (idpred) 
);
CREATE TABLE Pozoriste(
	 idpoz integer,
	 nazivpoz varchar2(40) NOT NULL,
	 adresapoz varchar2(30),
     	 sajt varchar2(40),
	 CONSTRAINT Pozoriste_PK PRIMARY KEY (idpoz) 
);	
CREATE TABLE Glumac(
	 mbg integer,
	 imeg varchar2(30) NOT NULL,
	 prezg varchar2(30) NOT NULL,
	 datumr date,     	 
 	 plata decimal(10, 2),
 	 dodatak decimal(10, 2),	  
	 status char(1),	
	 maticnopoz integer,
	 CONSTRAINT Glumac_PK PRIMARY KEY (mbg),
	 CONSTRAINT Poz_Glu_FK FOREIGN KEY (maticnopoz) REFERENCES Pozoriste (idpoz) 
); 
CREATE TABLE Scena(
	 idpoz integer,
	 nazivsce varchar2(50),
	 brojsed integer NOT NULL,
	 CONSTRAINT Scena_PK PRIMARY KEY (idpoz, nazivsce),
	 CONSTRAINT Poz_Sce_FK FOREIGN KEY (idpoz) REFERENCES Pozoriste (idpoz)	 
);
CREATE TABLE Uloga(
	 idpred integer,
	 imeulo varchar2(30),
	 vrstaulo char(1), 
 	 pol char(1), 
 	 CONSTRAINT Uloga_PK PRIMARY KEY (idpred, imeulo),
	 CONSTRAINT Ulo_Pred_FK FOREIGN KEY (idpred) REFERENCES Predstava (idpred)	 
);
CREATE TABLE Podela(
	 idpred integer,	 
 	 imeulo varchar2(30),
 	 mbg integer,	 
 	 honorar decimal(10, 2),
	 datumd date NOT NULL,
	 datump date, 
	 CONSTRAINT Podela_PK PRIMARY KEY (idpred, imeulo, mbg),
	 CONSTRAINT Pod_Ulo_FK FOREIGN KEY (idpred,imeulo) REFERENCES Uloga (idpred,imeulo),
	 CONSTRAINT Pod_Glum_FK FOREIGN KEY (mbg) REFERENCES Glumac (mbg)	 
);
CREATE TABLE Prikazivanje(	 
	 idpred integer,
	 rbr integer,
	 datumpri date NOT NULL,
	 vremepri varchar2(5) NOT NULL,  
	 brojgled integer, 
	 idpoz integer NOT NULL, 
	 nazivsce varchar2(50) NOT NULL,
	 CONSTRAINT Prikazivanje_PK PRIMARY KEY (rbr, idpred),
	 CONSTRAINT Prik_Pred_FK FOREIGN KEY (idpred) REFERENCES Predstava (idpred),
	 CONSTRAINT Sce_Prik_FK FOREIGN KEY (idpoz, nazivsce) REFERENCES Scena(idpoz, nazivsce)	 
);
CREATE TABLE Sektor(
	 idsek integer,
	 nazivsek varchar2(40) NOT NULL,
	 idpoz integer,	 
	 CONSTRAINT Sektor_PK PRIMARY KEY (idsek), 
	 CONSTRAINT Poz_Sec_FK FOREIGN KEY (idpoz) REFERENCES Pozoriste (idpoz)
);
CREATE TABLE Radnik(
	 mbr integer,
	 imer varchar2(30) NOT NULL,
	 prezr varchar2(30) NOT NULL,
	 platar decimal(10, 2),
 	 sef integer,
	 idsek integer,
	 CONSTRAINT Radnik_PK PRIMARY KEY (mbr),
	 CONSTRAINT Rad_Rad_FK FOREIGN KEY (sef) REFERENCES Radnik (mbr),	
	 CONSTRAINT Rad_Sek_FK FOREIGN KEY (idsek) REFERENCES Sektor(idsek)	  
);