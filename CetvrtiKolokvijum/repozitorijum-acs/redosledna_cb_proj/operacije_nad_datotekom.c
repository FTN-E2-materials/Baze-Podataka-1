#include "operacije_nad_datotekom.h"

void otvoriDatoteku(char *nazivDatotekeSaPodacima, FILE **datotekaSaPodacima, FILE **datotekaPromena, FILE **datotekaGresaka) {
	//otvori datoteku sa podacima
	*datotekaSaPodacima = fopen(nazivDatotekeSaPodacima, "rb+");
	if (*datotekaSaPodacima == NULL) {
		printf("Doslo je do greske! Moguce da datoteka \"%s\" ne postoji.\n", nazivDatotekeSaPodacima);
	} else {
		printf("Datoteka \"%s\" otvorena.\n", nazivDatotekeSaPodacima);
	}

	//otvori i datoteku promena
	char *nazivDatotekePromena = (char *)malloc(sizeof(strlen(nazivDatotekeSaPodacima)+strlen(".p")+1));
	sprintf(nazivDatotekePromena, "%s.p", nazivDatotekeSaPodacima);
	*datotekaPromena = fopen(nazivDatotekePromena, "rb+");
	if (*datotekaPromena == NULL) {
		printf("Doslo je do greske! Moguce da datoteka promena \"%s\" ne postoji.\n", nazivDatotekePromena);
	} else {
		printf("Datoteka promena \"%s\" otvorena.\n", nazivDatotekePromena);
	}
	free(nazivDatotekePromena);

	//otvori i datoteku gresaka
	char *nazivDatotekeGresaka = (char *)malloc(sizeof(strlen(nazivDatotekeSaPodacima)+strlen(".e")+1));
	sprintf(nazivDatotekeGresaka, "%s.e", nazivDatotekeSaPodacima);
	*datotekaGresaka = fopen(nazivDatotekeGresaka, "rb+");
	if (*datotekaGresaka == NULL) {
		printf("Doslo je do greske! Moguce da datoteka gresaka \"%s\" ne postoji.\n", nazivDatotekeGresaka);
	} else {
		printf("Datoteka gresaka \"%s\" otvorena.\n", nazivDatotekeGresaka);
	}
	free(nazivDatotekeGresaka);
}

void kreirajDatoteku(char *nazivDatotekeSaPodacima) {
	//kreiraj dadoteku sa podacima
	FILE *datotekaSaPodacima = fopen(nazivDatotekeSaPodacima, "wb");
	if (datotekaSaPodacima == NULL) {
		printf("Doslo je do greske prilikom kreiranja datoteke \"%s\"!\n", nazivDatotekeSaPodacima);
	} else {
		//upisi pocetni blok
		BLOK_DATOTEKE_SA_PODACIMA blok_s;
		strcpy(blok_s.slogovi[0].evidBroj, OZNAKA_KRAJA_DATOTEKE);
		fwrite(&blok_s, sizeof(BLOK_DATOTEKE_SA_PODACIMA), 1, datotekaSaPodacima);
		printf("Datoteka \"%s\" uspesno kreirana.\n", nazivDatotekeSaPodacima);
		fclose(datotekaSaPodacima);
	}

	//kreiraj i datoteku promena
	char *nazivDatotekePromena = (char *)malloc(sizeof(strlen(nazivDatotekeSaPodacima)+strlen(".p")+1));
	sprintf(nazivDatotekePromena, "%s.p", nazivDatotekeSaPodacima);
	FILE *datotekaPromena = fopen(nazivDatotekePromena, "wb");
	if (datotekaPromena == NULL) {
		printf("Doslo je do greske prilikom kreiranja datoteke promena \"%s\"!\n", nazivDatotekePromena);
	} else {
		//upisi pocetni blok
		BLOK_DATOTEKE_PROMENA blokDatotekePromena;
		strcpy(blokDatotekePromena.slogovi[0].evidBroj, OZNAKA_KRAJA_DATOTEKE);
		fwrite(&blokDatotekePromena, sizeof(BLOK_DATOTEKE_PROMENA), 1, datotekaPromena);
		printf("Datoteka promena \"%s\" uspesno kreirana.\n", nazivDatotekePromena);
		fclose(datotekaPromena);
	}
	free(nazivDatotekePromena);

	//kreiraj i datoteku gresaka
	char *nazivDatotekeGresaka = (char *)malloc(sizeof(strlen(nazivDatotekeSaPodacima)+strlen(".e")+1));
	sprintf(nazivDatotekeGresaka, "%s.e", nazivDatotekeSaPodacima);
	FILE *datotekaGresaka = fopen(nazivDatotekeGresaka, "wb");
	if (datotekaGresaka == NULL) {
		printf("Doslo je do greske prilikom kreiranja datoteke gresaka \"%s\"!\n", nazivDatotekeGresaka);
	} else {
		//ova datoteka ne mora biti blokirana
		printf("Datoteka gresaka \"%s\" uspesno kreirana.\n", nazivDatotekeGresaka);
		fclose(datotekaGresaka);
	}
	free(nazivDatotekeGresaka);

}

SLOG_DATOTEKE_SA_PODACIMA *pronadjiSlog(FILE *datotekaSaPodacima, char *evidBroj) {
	if (datotekaSaPodacima == NULL) {
		return NULL;
	}

	fseek(datotekaSaPodacima, 0, SEEK_SET);
	BLOK_DATOTEKE_SA_PODACIMA blok;

	while (fread(&blok, sizeof(BLOK_DATOTEKE_SA_PODACIMA), 1, datotekaSaPodacima)) {

		for (int i = 0; i < FBLOKIRANJA_S; i++) {
			if (strcmp(blok.slogovi[i].evidBroj, OZNAKA_KRAJA_DATOTEKE) == 0 ||
                atoi(blok.slogovi[i].evidBroj) > atoi(evidBroj)) {
				//nema vise slogova, ili smo prosli poziciju na kojoj bi treba biti trazeni slog
				return NULL;
			}

			if (strcmp(blok.slogovi[i].evidBroj, evidBroj) == 0) {
				SLOG_DATOTEKE_SA_PODACIMA *slog = (SLOG_DATOTEKE_SA_PODACIMA *)malloc(sizeof(SLOG_DATOTEKE_SA_PODACIMA));
				memcpy(slog, &blok.slogovi[i], sizeof(SLOG_DATOTEKE_SA_PODACIMA));
				return slog;
			}
		}
	}
	return NULL;
}

void unesiNoviSlog(FILE *datotekaPromena, char *evidBroj, DOLAZAK *dolazak) {
	if (datotekaPromena == NULL) {
		printf("Datoteka promena nije otvorena!\n");
		return;
	}

	SLOG_DATOTEKE_PROMENA slog;
	strcpy(slog.evidBroj, evidBroj);
	memcpy(&slog.dolazak, dolazak, sizeof(DOLAZAK));
	slog.op = OP_NOVI_SLOG;

	dodajSlogUDatotekuPromena(datotekaPromena, &slog);
}

//dodaje slogDatotekePromena u datoteku promena (serijska datoteka)
void dodajSlogUDatotekuPromena(FILE *datotekaPromena, SLOG_DATOTEKE_PROMENA *slogDatotekePromena) {

	BLOK_DATOTEKE_PROMENA blok;
	fseek(datotekaPromena, -sizeof(BLOK_DATOTEKE_PROMENA), SEEK_END); //u poslenji blok upisujemo novi slog
	fread(&blok, sizeof(BLOK_DATOTEKE_PROMENA), 1, datotekaPromena);
	SLOG_DATOTEKE_PROMENA krajDatoteke;
	int i;
	for (i = 0; i < FBLOKIRANJA_P; i++) {
		if (strcmp(blok.slogovi[i].evidBroj, OZNAKA_KRAJA_DATOTEKE) == 0) {
			//Sacuvacemo kraj datoteke, da ga upisemo posle novododatog sloga
			memcpy(&krajDatoteke, &blok.slogovi[i], sizeof(SLOG_DATOTEKE_PROMENA));
			memcpy(&blok.slogovi[i], slogDatotekePromena, sizeof(SLOG_DATOTEKE_PROMENA));
			break;
		}
	}

	i++; //(na to mesto u bloku cemo upisati krajDatoteke)

	if (i < FBLOKIRANJA_P) {
        //Jos uvek ima mesta u ovom bloku, mozemo tu smestiti slog
        //sa oznakom kraja datoteke.
		memcpy(&blok.slogovi[i], &krajDatoteke, sizeof(SLOG_DATOTEKE_PROMENA));

		//Sada blok mozemo vratiti u datoteku.
		fseek(datotekaPromena, -sizeof(BLOK_DATOTEKE_PROMENA), SEEK_CUR);
		fwrite(&blok, sizeof(BLOK_DATOTEKE_PROMENA), 1, datotekaPromena);
	} else {
		//Nema vise mesta u tom bloku, tako da moramo
        //praviti novi blok u koji cemo upisati slog
        //sa oznakom kraja datoteke.

		//Prvo ipak moramo vratiti u datoteku blok
        //koji smo upravo popunili:
		fseek(datotekaPromena, -sizeof(BLOK_DATOTEKE_PROMENA), SEEK_CUR);
		fwrite(&blok, sizeof(BLOK_DATOTEKE_PROMENA), 1, datotekaPromena);

		//Okej, sad pravimo novi blok
		BLOK_DATOTEKE_PROMENA noviBlok;
		memcpy(&noviBlok.slogovi[0], &krajDatoteke, sizeof(SLOG_DATOTEKE_PROMENA));
		//(vec smo na kraju datoteke, nema potrebe za fseekom)
		fwrite(&noviBlok, sizeof(BLOK_DATOTEKE_PROMENA), 1, datotekaPromena);
	}

}

void ispisiSveSlogove(FILE *datotekaSaPodacima, FILE *datotekaPromena, FILE *datotekaGresaka) {
	if (datotekaSaPodacima == NULL || datotekaPromena == NULL || datotekaGresaka == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	//datoteka sa podacima
	printf("Datoteka sa podacima (Ds)\n");
	fseek(datotekaSaPodacima, 0, SEEK_SET);
	BLOK_DATOTEKE_SA_PODACIMA blok_s;
	int rbBloka = 0;
	printf("BL SL Evid.Br   Sif.Zat      Dat.Vrem.Dol  Celija  Kazna\n");
	while (1) {
		fread(&blok_s, sizeof(BLOK_DATOTEKE_SA_PODACIMA), 1, datotekaSaPodacima);
		if (ferror(datotekaSaPodacima)) {
			printf("Doslo je do greske!\n");
			return;
		}
		if (feof(datotekaSaPodacima)) {
			//kraj datoteke
			break;
		}
		for (int i = 0; i < FBLOKIRANJA_S; i++) {
			if (strcmp(blok_s.slogovi[i].evidBroj, OZNAKA_KRAJA_DATOTEKE) == 0) {
				printf("B%d S%d *\n", rbBloka, i);
                break; //citaj sledeci blok
			}

			printf("B%d S%d %8s  ", rbBloka, i, blok_s.slogovi[i].evidBroj);
			ispisiDolazak(&blok_s.slogovi[i].dolazak);
			printf("\n");
		}

		rbBloka++;
	}
	printf("\n");

	printf("Datoteka promena (Dp)\n");
	//datoteka promena
	fseek(datotekaPromena, 0, SEEK_SET);
	BLOK_DATOTEKE_PROMENA blokDatotekePromena;
	rbBloka = 0;
	printf("BL SL Evid.Br   Sif.Zat      Dat.Vrem.Dol  Celija  Kazna  OP\n");
	while (1) {
		fread(&blokDatotekePromena, sizeof(BLOK_DATOTEKE_PROMENA), 1, datotekaPromena);
		if (ferror(datotekaPromena)) {
			printf("Doslo je do greske!\n");
			break;
		}
		if (feof(datotekaPromena)) {
			//kraj datoteke
			break;
		}

		for (int i = 0; i < FBLOKIRANJA_S; i++) {
			if (strcmp(blokDatotekePromena.slogovi[i].evidBroj, OZNAKA_KRAJA_DATOTEKE) == 0) {
				printf("B%d S%d *\n", rbBloka, i);
                break; //citaj sledeci blok
			}

			printf("B%d S%d %s  ",
				rbBloka, i,
				blokDatotekePromena.slogovi[i].evidBroj);
			if (blokDatotekePromena.slogovi[i].op == OP_NOVI_SLOG) {
				ispisiDolazak(&blokDatotekePromena.slogovi[i].dolazak);
				printf("  ");
			} else if (blokDatotekePromena.slogovi[i].op == OP_MODIFIKACIJA) {
				printf("                          %7s %6d  ",
				blokDatotekePromena.slogovi[i].dolazak.oznakaCelije,
				blokDatotekePromena.slogovi[i].dolazak.duzinaKazne);
			} else if (blokDatotekePromena.slogovi[i].op == OP_BRISANJE) {
				printf("                                          ");
			}
			printf("%s\n",
				blokDatotekePromena.slogovi[i].op == OP_NOVI_SLOG ? "NSL" :
					(blokDatotekePromena.slogovi[i].op == OP_MODIFIKACIJA ? "MOD" : "BRS"));
		}

		rbBloka++;
	}
	printf("\n");

	printf("Datoteka gresaka (De)\n");
	fseek(datotekaGresaka, 0, SEEK_SET);
	SLOG_DATOTEKE_PROMENA slogDatotekePromena;
	int rbSloga = 0;
	printf("SLOG  Evid.Br   Sif.Zat      Dat.Vrem.Dol  Celija  Kazna  OP\n");
	while (1) {
		fread(&slogDatotekePromena, sizeof(SLOG_DATOTEKE_PROMENA), 1, datotekaGresaka);
		if (feof(datotekaGresaka)) {
			break;
		}

		printf("%4d  %s  ",
				rbSloga,
				slogDatotekePromena.evidBroj);
		if (slogDatotekePromena.op == OP_NOVI_SLOG) {
			ispisiDolazak(&slogDatotekePromena.dolazak);
			printf("  ");
		} else if (slogDatotekePromena.op == OP_MODIFIKACIJA) {
			printf("                          %7s %6d  ",
			slogDatotekePromena.dolazak.oznakaCelije,
			slogDatotekePromena.dolazak.duzinaKazne);
		} else if (slogDatotekePromena.op == OP_BRISANJE) {
			printf("                                          ");
		}
		printf("%s\n",
			slogDatotekePromena.op == OP_NOVI_SLOG ? "NSL" :
				(slogDatotekePromena.op == OP_MODIFIKACIJA ? "MOD" : "BRS"));

		rbSloga++;
	}
	printf("\n");
}

void ispisiSlog(FILE *datotekaSaPodacima, char *evidBroj) {
	if (datotekaSaPodacima == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	SLOG_DATOTEKE_SA_PODACIMA *slog = pronadjiSlog(datotekaSaPodacima, evidBroj);
	if (slog == NULL) {
		printf("Nema tog sloga u datoteci!\n");
		return;
	}
	printf("Evid.Br   Sif.Zat      Dat.Vrem.Dol  Celija  Kazna\n");
	printf("%8s  ", slog->evidBroj);
	ispisiDolazak(&slog->dolazak);
	printf("\n");
}

void ispisiDolazak(DOLAZAK *dolazak) {
	printf("%7s  %02d-%02d-%4d %02d:%02d %7s %6d",
		dolazak->sifraZatvorenika,
		dolazak->datumVremeDolaska.dan,
		dolazak->datumVremeDolaska.mesec,
		dolazak->datumVremeDolaska.godina,
		dolazak->datumVremeDolaska.sati,
		dolazak->datumVremeDolaska.minuti,
		dolazak->oznakaCelije,
		dolazak->duzinaKazne);
}


void azurirajSlog(FILE *datotekaPromena, char *evidBroj, char *oznakaCelije, int duzinaKazne) {
	if (datotekaPromena == NULL) {
		printf("Datoteka promena nije otvorena!\n");
		return;
	}

	SLOG_DATOTEKE_PROMENA slog;
	strcpy(slog.evidBroj, evidBroj);
	DOLAZAK tmp;
	tmp.duzinaKazne = duzinaKazne;
	strcpy(tmp.oznakaCelije, oznakaCelije);
	memcpy(&slog.dolazak, &tmp, sizeof(DOLAZAK));
	slog.op = OP_MODIFIKACIJA;

	dodajSlogUDatotekuPromena(datotekaPromena, &slog);
}

void obrisiSlog(FILE *datotekaPromena, char *evidBroj) {
	if (datotekaPromena == NULL) {
		printf("Datoteka promena nije otvorena!\n");
		return;
	}

	SLOG_DATOTEKE_PROMENA slog;
	strcpy(slog.evidBroj, evidBroj);
	slog.op = OP_BRISANJE;

	dodajSlogUDatotekuPromena(datotekaPromena, &slog);
}

void sortirajDatotekuPromena(FILE **datotekaPromena, char *nazivDatotekeSaPodacima) {
	//celu datoteku u memoriju pa cemo je tu sortirati
	//(vrv nije velika)

	//niz za slogove
	fseek(*datotekaPromena, 0, SEEK_END);
	long fileSize = ftell(*datotekaPromena);
	int ppBrojSlogova = (int)((float)fileSize/(float)sizeof(SLOG_DATOTEKE_PROMENA))+1; //+1 za svaki slucaj
	SLOG_DATOTEKE_PROMENA *slogovi = (SLOG_DATOTEKE_PROMENA *)malloc(sizeof(SLOG_DATOTEKE_PROMENA) * ppBrojSlogova);
	int slogoviIndex = 0;
	//printf("ppbrojslogova : %d\n", ppBrojSlogova);


	//citaj sve
	fseek(*datotekaPromena, 0, SEEK_SET);
	BLOK_DATOTEKE_PROMENA blok;
	while(1) {
		fread(&blok, sizeof(BLOK_DATOTEKE_PROMENA), 1, *datotekaPromena);
		if (ferror(*datotekaPromena) || feof(*datotekaPromena)) {
			break;
		}

		for (int i = 0; i < FBLOKIRANJA_P; i++) {
			//printf("aaa %d\n", slogoviIndex);
			memcpy(&slogovi[slogoviIndex], &blok.slogovi[i], sizeof(SLOG_DATOTEKE_PROMENA));
			slogoviIndex++;
			//printf("bbb %d\n", slogoviIndex);

			if (strcmp(blok.slogovi[i].evidBroj, OZNAKA_KRAJA_DATOTEKE) == 0) {
				break;
			}
		}
	}

	//bubble sort (stabilni)
	SLOG_DATOTEKE_PROMENA tmp;
	int i, j;
	for (i = 0; i < slogoviIndex-1; i++) {
    	for (j = 0; j < slogoviIndex-i-1; j++) {
			if (strcmp(slogovi[j+1].evidBroj, OZNAKA_KRAJA_DATOTEKE) == 0) {
				continue;
			}
          	if (atoi(slogovi[j].evidBroj) > atoi(slogovi[j+1].evidBroj)) {
				memcpy(&tmp, &slogovi[j], sizeof(SLOG_DATOTEKE_PROMENA));
				memcpy(&slogovi[j], &slogovi[j+1], sizeof(SLOG_DATOTEKE_PROMENA));
				memcpy(&slogovi[j+1], &tmp, sizeof(SLOG_DATOTEKE_PROMENA));
			}
		}
	}

	//resetuj datotekaPromena (obrisi sadrzaj i onda upisi pocetni blok)
	fclose(*datotekaPromena);
	*datotekaPromena = NULL;
	char *nazivDatotekePromena = (char *)malloc(sizeof(char)*(strlen(nazivDatotekeSaPodacima)+strlen(".p")+1));
	sprintf(nazivDatotekePromena, "%s.p", nazivDatotekeSaPodacima);
	*datotekaPromena = fopen(nazivDatotekePromena, "wb+");

	BLOK_DATOTEKE_PROMENA blokDatotekePromena;
	strcpy(blokDatotekePromena.slogovi[0].evidBroj, OZNAKA_KRAJA_DATOTEKE);
	fwrite(&blokDatotekePromena, sizeof(BLOK_DATOTEKE_PROMENA), 1, *datotekaPromena);
	free(nazivDatotekePromena);


	//upisi sve
	for (int i = 0; i < slogoviIndex-1; i++) {
		//-1 da ne upisuje onaj slog sa oznakom kraja (jer vec postoji)
		//printf("kljuc %s - op:[%s]\n", slogovi[i].evidBroj, slogovi[i].op==OP_NOVI_SLOG?"N":(slogovi[i].op==OP_MODIFIKACIJA?"M":"B"));
		dodajSlogUDatotekuPromena(*datotekaPromena, &slogovi[i]);

	}

	printf("Datoteka promena sortirana.\n");

}

void redoslednaObrada(FILE **datotekaPromena, FILE **datotekaSaPodacima, FILE **datotekaGresaka, char *nazivDatotekeSaPodacima) {


	//ocisti datoteku gresaka
	fclose(*datotekaGresaka);
	*datotekaGresaka = NULL;
	char *nazivDatotekeGresaka = (char *)malloc(sizeof(char) * (strlen(nazivDatotekeSaPodacima)+(strlen(".e"))+1));
	sprintf(nazivDatotekeGresaka, "%s.e", nazivDatotekeSaPodacima);
	*datotekaGresaka = fopen(nazivDatotekeGresaka, "wb+");
	free(nazivDatotekeGresaka);

	//napravi novu datotetu
	FILE *novaDatotekaSaPodacima = fopen(FAJL_N_FILENAME, "wb+");

	//ALOGIRTAM BEGIN

	//u datoteku gresaka samo pisem svaki
	//"pogresan" slogDatotekePromena, jedan za drugim,
	//neblokirano
	fseek(*datotekaGresaka, 0, SEEK_SET);

	SLOG_DATOTEKE_PROMENA slogDatotekePromena;	//slog iz datoteke promena
	SLOG_DATOTEKE_SA_PODACIMA slogDatotekeSaPodacima;	//slog iz stare datoteke
	SLOG_DATOTEKE_SA_PODACIMA slogNovaDatoteka;	//slog za upis u novu datoteku

	//kako treba da citamo/pisemo REDOM slogove
	//u ovim datotekama, koristicemo funckije
	//citajSlogNaPozicijiIzDatotekeSaPodacima/citajSlogNaPozicijiIzDatotekePromena/pisiNaPozicijiuUDatotekuSaPodacima
	//koje citaju/pisu SLOG sa zadatim rednim
	//brojem u fajlu (zato vodimo racuna
	//dokle smo stigli sa citanjem pomocu
	//promenljivih pozicijaUDatoteciPromena/pozicijaUDatoteciSaPodacima/pozicijaUNovojDatoteciSaPodacima)
	int pozicijaUDatoteciPromena = 0, pozicijaUDatoteciSaPodacima = 0, pozicijaUNovojDatoteciSaPodacima = 0;

	//te fje kao povratnu vrednost vracaju
	//1 trazeni slog ne postoji, ili smo
	//stigli do sloga sa oznakom kraja datoteke
	int indikatorKrajaDatotekeSaPodacima = 0, indikatorKrajaDatotekePromena = 0;

	indikatorKrajaDatotekePromena = citajSlogNaPozicijiIzDatotekePromena(*datotekaPromena, &slogDatotekePromena, pozicijaUDatoteciPromena++);
	indikatorKrajaDatotekeSaPodacima = citajSlogNaPozicijiIzDatotekeSaPodacima(*datotekaSaPodacima, &slogDatotekeSaPodacima, pozicijaUDatoteciSaPodacima++);

	while (1) { //str 203: azuriranje
		if (indikatorKrajaDatotekePromena && indikatorKrajaDatotekeSaPodacima) {
			break;
		}

		if (atoi(slogDatotekePromena.evidBroj) == atoi(slogDatotekeSaPodacima.evidBroj)) {
			//poklapaju se;
			//sad cemo slogDatotekeSaPodacima staviti u slogNovaDatoteka
			//i primeniti na njega sve promene iz Dp
			//(koje se na njega odnose)
			memcpy(&slogNovaDatoteka, &slogDatotekeSaPodacima, sizeof(SLOG_DATOTEKE_SA_PODACIMA));

			//da nema promena, samo bi ga upisali u Dn
			int pisiOvajSlogUNovuDatoteku = 1;

			//posto cemo citati promene, treba stati
			//kad naidjemo na promenu koja se NE odnosi
			//na ovaj slogDatotekeSaPodacima (pa zato njegov evidBroj)
			char evidBrojSlogaZaObradu[8+1];
			strcpy(evidBrojSlogaZaObradu, slogNovaDatoteka.evidBroj);

			if (!indikatorKrajaDatotekePromena) {
				//ako ima promena, primeni te promene na slogDatotekePromena

				while (1) {
					if (indikatorKrajaDatotekePromena || atoi(slogDatotekePromena.evidBroj)!=atoi(evidBrojSlogaZaObradu)) {
						//ako vise nema promena (uopste) ili
						//ova promena koju smo procitali se i ne
						//odnosi na slogDatotekePromena, prekidaj!
						break;
					}
					if (pisiOvajSlogUNovuDatoteku) {
						if (slogDatotekePromena.op == OP_BRISANJE) {
							pisiOvajSlogUNovuDatoteku = 0;
						} else if (slogDatotekePromena.op == OP_MODIFIKACIJA) {
							strcpy(slogNovaDatoteka.dolazak.oznakaCelije, slogDatotekePromena.dolazak.oznakaCelije);
							slogNovaDatoteka.dolazak.duzinaKazne = slogDatotekePromena.dolazak.duzinaKazne;
						} else if (slogDatotekePromena.op == OP_NOVI_SLOG) {
							//greska: pokusaj upisa postojeceg sloga
							fwrite(&slogDatotekePromena, sizeof(SLOG_DATOTEKE_PROMENA), 1, *datotekaGresaka);
						}
					} else {
						if (slogDatotekePromena.op == OP_NOVI_SLOG) {
							pisiOvajSlogUNovuDatoteku = 1;
							memcpy(&slogNovaDatoteka, &slogDatotekePromena, sizeof(SLOG_DATOTEKE_SA_PODACIMA));
						} else {
							//greska: pokusaj mod/bris nepostojeceg sloga
							fwrite(&slogDatotekePromena, sizeof(SLOG_DATOTEKE_PROMENA), 1, *datotekaGresaka);
						}
					}
					//citaj sledecu promenu da primenis na njega
					indikatorKrajaDatotekePromena = citajSlogNaPozicijiIzDatotekePromena(*datotekaPromena, &slogDatotekePromena, pozicijaUDatoteciPromena++);
				}

				//u slogNovaDatoteka sada stoji slog iz slogDatotekeSaPodacima ili slogDatotekePromena
				//(ako je postojao neki slogDatotekePromena sa OP_NOVI_SLOG);
				//mozemo ga upisati u fajl Dn
				if (pisiOvajSlogUNovuDatoteku) {
					pisiNaPozicijiuUDatotekuSaPodacima(novaDatotekaSaPodacima, &slogNovaDatoteka, pozicijaUNovojDatoteciSaPodacima++);
				}

			}

			//taj slogDatotekeSaPodacima je obradjen, predji na sledeci
			indikatorKrajaDatotekeSaPodacima = citajSlogNaPozicijiIzDatotekeSaPodacima(*datotekaSaPodacima, &slogDatotekeSaPodacima, pozicijaUDatoteciSaPodacima++);

		} else if (atoi(slogDatotekeSaPodacima.evidBroj) < atoi(slogDatotekePromena.evidBroj)) {
			//slogDatotekePromena je nesto ispred slogDatotekeSaPodacima;
			//ta promena treba da se primeni na nesto
			//ispred slogDatotekeSaPodacima;
			//upisi slogDatotekeSaPodacima u fajl Dn (takav kakav je)
			//i citaj sledeci slogDatotekeSaPodacima
			//(mozda je za njega ova promena u slogDatotekePromena?)

			if (!indikatorKrajaDatotekeSaPodacima) {
				//(zapravo smo procitali nesto iz Ds)

				pisiNaPozicijiuUDatotekuSaPodacima(novaDatotekaSaPodacima, &slogDatotekeSaPodacima, pozicijaUNovojDatoteciSaPodacima++);
				indikatorKrajaDatotekeSaPodacima = citajSlogNaPozicijiIzDatotekeSaPodacima(*datotekaSaPodacima, &slogDatotekeSaPodacima, pozicijaUDatoteciSaPodacima++);
			}

		} else {

			//slogDatotekePromena je nesto pre slogDatotekeSaPodacima;
			//promena koju treba primenimo
			//se mozda odnosi na nesto sto smo vec prosli
			//(nikako ne moze biti slucaj, imalo smo gore proveru ==),
			//ili na nesto sto uopste i nije bilo u Ds
			//(to je ono sto se desava)

			//opet pripremamo ovu promenljivu, za slucaj da
			//je ta promena upisu novog sloga
			int pisiOvajSlogUNovuDatoteku = 0;

			//opet, ako imamo upis novog sloga,
			//na njega cemo primeniti neke promene,
			//pa treba znati kad stati sa promenama
			char evidBrojSlogaZaObradu[8+1];
			strcpy(evidBrojSlogaZaObradu, slogDatotekePromena.evidBroj);

			if (!indikatorKrajaDatotekePromena) {
				//(zaista imamo promenu(!))

				while (1) {
					if (indikatorKrajaDatotekePromena || atoi(slogDatotekePromena.evidBroj)!=atoi(evidBrojSlogaZaObradu)) {
						//ako vise nema promena (uopste) ili
						//ova promena koju smo procitali se i ne
						//odnosi na slogDatotekePromena, prekidaj!
						break;
					}
					if (pisiOvajSlogUNovuDatoteku) {
						//u ovom bloku se menja ili brise
						//slogNovaDatoteka;
						//u prvom prolazu, interesuje nas samo
						//promena OP_NOVI_SLOG, da bi uopste imali
						//sta da modifikujemo ili brisemo, tako
						//da u tom momentu ovaj blok ne radi nista

						if (slogDatotekePromena.op == OP_BRISANJE) {
							pisiOvajSlogUNovuDatoteku = 0;
						} else if (slogDatotekePromena.op == OP_MODIFIKACIJA) {
							strcpy(slogNovaDatoteka.dolazak.oznakaCelije, slogDatotekePromena.dolazak.oznakaCelije);
							slogNovaDatoteka.dolazak.duzinaKazne = slogDatotekePromena.dolazak.duzinaKazne;
						} else if (slogDatotekePromena.op == OP_NOVI_SLOG) {
							//greska: pokusaj upisa postojeceg sloga
							fwrite(&slogDatotekePromena, sizeof(SLOG_DATOTEKE_PROMENA), 1, *datotekaGresaka);
						}
					} else {

						//ocekujemo da u prvom prolazu ovog while(1)
						//promena bude neki OP_NOVI_SLOG, pa ce nam
						//on namestiti slogNovaDatoteka koji cemo posle menjati
						//promenama tipa OP_MODIFIKACIJA ili OP_BRISANJE

						if (slogDatotekePromena.op == OP_NOVI_SLOG) {
							pisiOvajSlogUNovuDatoteku = 1;
							memcpy(&slogNovaDatoteka, &slogDatotekePromena, sizeof(SLOG_DATOTEKE_SA_PODACIMA));
						} else {
							//greska: pokusaj mod/bris nepostojeceg sloga
							fwrite(&slogDatotekePromena, sizeof(SLOG_DATOTEKE_PROMENA), 1, *datotekaGresaka);
						}
					}
					//citaj sledecu promenu da primenis na njega
					indikatorKrajaDatotekePromena = citajSlogNaPozicijiIzDatotekePromena(*datotekaPromena, &slogDatotekePromena, pozicijaUDatoteciPromena++);
				}

				//u slogNovaDatoteka sada stoji slog iz slogDatotekePromena
				//(ako je postojao neki slogDatotekePromena sa OP_NOVI_SLOG);
				//mozemo ga upisati u fajl Dn
				if (pisiOvajSlogUNovuDatoteku) {
					pisiNaPozicijiuUDatotekuSaPodacima(novaDatotekaSaPodacima, &slogNovaDatoteka, pozicijaUNovojDatoteciSaPodacima++);
				}

			}

		}


		if (indikatorKrajaDatotekePromena) {
			strcpy(slogDatotekePromena.evidBroj, OZNAKA_KRAJA_DATOTEKE);
		}

		if (indikatorKrajaDatotekeSaPodacima) {
			strcpy(slogDatotekeSaPodacima.evidBroj, OZNAKA_KRAJA_DATOTEKE);
		}


	} //str 203: azuriranje KRAJ

	//dodaj jos slog sa oznakom kraja u Dn
	strcpy(slogNovaDatoteka.evidBroj, OZNAKA_KRAJA_DATOTEKE);
	pisiNaPozicijiuUDatotekuSaPodacima(novaDatotekaSaPodacima, &slogNovaDatoteka, pozicijaUNovojDatoteciSaPodacima++);

	//ALOGITAM END

	//zatvori "novu" datoteku
	fclose(novaDatotekaSaPodacima);
}

void isprazniDatotekuPromena(FILE **datotekaPromena, char *nazivDatotekeSaPodacima) {

	fclose(*datotekaPromena);
	*datotekaPromena = NULL;
	char *nazivDatotekePromena = (char *)malloc(sizeof(char)*(strlen(nazivDatotekeSaPodacima)+strlen(".p")+1));
	sprintf(nazivDatotekePromena, "%s.p", nazivDatotekeSaPodacima);
	*datotekaPromena = fopen(nazivDatotekePromena, "wb+");

	BLOK_DATOTEKE_PROMENA blokDatotekePromena;
	strcpy(blokDatotekePromena.slogovi[0].evidBroj, OZNAKA_KRAJA_DATOTEKE);
	fwrite(&blokDatotekePromena, sizeof(BLOK_DATOTEKE_PROMENA), 1, *datotekaPromena);
	free(nazivDatotekePromena);
}

void zameniStaruDatotekuPodacimaNovom(FILE **datotekaSaPodacima, char *nazivDatotekeSaPodacima) {

	fclose(*datotekaSaPodacima);				                   	//zatvori stari fajl
	*datotekaSaPodacima = NULL;
	remove(nazivDatotekeSaPodacima);					            //obrisi ga
	rename(FAJL_N_FILENAME, nazivDatotekeSaPodacima);	            //primenuj novi u stari

	*datotekaSaPodacima = fopen(nazivDatotekeSaPodacima, "rb+");	//otvori taj novi

}

//str 146
int citajSlogNaPozicijiIzDatotekeSaPodacima(FILE *datotekaSaPodacima, SLOG_DATOTEKE_SA_PODACIMA *slogDatotekeSaPodacima, int p) {
	//citaj slog na poziciji p

	int rbBloka = (int)floor((float)p/(float)FBLOKIRANJA_S);
	int rbSlogaUBloku = p%FBLOKIRANJA_S;

	BLOK_DATOTEKE_SA_PODACIMA blok_s;
	fseek(datotekaSaPodacima, rbBloka*sizeof(BLOK_DATOTEKE_SA_PODACIMA), SEEK_SET);
	int procitanoPodataka = fread(&blok_s, sizeof(BLOK_DATOTEKE_SA_PODACIMA), 1, datotekaSaPodacima);
	if (procitanoPodataka == 0 ||
		strcmp(
			blok_s.slogovi[rbSlogaUBloku].evidBroj,
			OZNAKA_KRAJA_DATOTEKE) == 0) {
		return 1;
	}

	memcpy(slogDatotekeSaPodacima, &blok_s.slogovi[rbSlogaUBloku], sizeof(SLOG_DATOTEKE_SA_PODACIMA));

	return 0;
}

int citajSlogNaPozicijiIzDatotekePromena(FILE *datotekaPromena, SLOG_DATOTEKE_PROMENA *slogDatotekePromena, int p) {
	int rbBloka = (int)floor((float)p/(float)FBLOKIRANJA_P);
	int rbSlogaUBloku = p%FBLOKIRANJA_P;

	BLOK_DATOTEKE_PROMENA blokDatotekePromena;
	fseek(datotekaPromena, rbBloka*sizeof(BLOK_DATOTEKE_PROMENA), SEEK_SET);
	int procitanoPodataka = fread(&blokDatotekePromena, sizeof(BLOK_DATOTEKE_PROMENA), 1, datotekaPromena);
	if (procitanoPodataka == 0 ||
		strcmp(
			blokDatotekePromena.slogovi[rbSlogaUBloku].evidBroj,
			OZNAKA_KRAJA_DATOTEKE) == 0) {
		return 1;
	}

	memcpy(slogDatotekePromena, &blokDatotekePromena.slogovi[rbSlogaUBloku], sizeof(SLOG_DATOTEKE_PROMENA));

	return 0;
}

void pisiNaPozicijiuUDatotekuSaPodacima(FILE *datotekaSaPodacima, SLOG_DATOTEKE_SA_PODACIMA *slogDatotekeSaPodacima, int p) {

	int rbBloka = (int)floor((float)p/(float)FBLOKIRANJA_S);
	int rbSlogaUBloku = p%FBLOKIRANJA_S;

	BLOK_DATOTEKE_SA_PODACIMA blok_s;
	fseek(datotekaSaPodacima, rbBloka*sizeof(BLOK_DATOTEKE_SA_PODACIMA), SEEK_SET);
	fread(&blok_s, sizeof(BLOK_DATOTEKE_SA_PODACIMA), 1, datotekaSaPodacima);
	//ako nema tog bloga i dobijemo feof,
	//nije problem, svejedno cemo na to mesto
	//upisati blok_s za koji sekund
	memcpy(&blok_s.slogovi[rbSlogaUBloku], slogDatotekeSaPodacima, sizeof(SLOG_DATOTEKE_SA_PODACIMA));
	fseek(datotekaSaPodacima, rbBloka*sizeof(BLOK_DATOTEKE_SA_PODACIMA), SEEK_SET);
	fwrite(&blok_s, sizeof(BLOK_DATOTEKE_SA_PODACIMA), 1, datotekaSaPodacima);

}
