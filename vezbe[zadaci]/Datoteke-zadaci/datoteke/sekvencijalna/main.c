#include <stdio.h>
#include <stdlib.h>

#include "operacije_nad_datotekom.h"

// gcc *.c && ./a.out

int main() {

	int running = 1;
	int userInput;

	FILE *fajl = NULL;

	while (running) {
		printf("Odaberite opciju:\n");
		printf("1 - Otvaranje datoteke\n");
		printf("2 - Formiranje datoteke\n");
		printf("3 - Pretraga datoteke\n");
		printf("4 - Unos sloga\n");
		printf("5 - Ispis svi slogova\n");
		printf("6 - Azuriranje sloga\n");
		printf("7 - Brisanje sloga (logicko)\n");
		printf("8 - Brisanje sloga (fiziko)\n");
		printf("9 - Reorgranizuj datoteku\n");
		printf("0 - Izlaz\n");
		if (fajl == NULL) {
			printf("!!! PAZNJA: datoteka jos uvek nije otvorena !!!\n");
		}
		scanf("%d", &userInput);
		getc(stdin);

		switch(userInput) {
			case 1:
				{
					char filename[20];
					printf("Unesite ime datoteke za otvaranje: ");
					scanf("%s", &filename[0]);
					fajl = otvoriDatoteku(filename);
                    printf("\n");
					break;
				}
			case 2:
				{
					char filename[20];
					printf("Unesite ime datoteke za kreiranje: ");
					scanf("%s", filename);
					kreirajDatoteku(filename);
                    printf("\n");
					break;
				}
			case 3:
				{
					char evidBroj[8+1];
					printf("Unesite evid. broj trazenog prijema: ");
					scanf("%s", evidBroj);
					SLOG *slog = pronadjiSlog(fajl, evidBroj);
					if (slog == NULL) {
                        printf("Trazeni slog ne postoji!\n");
					} else {
					    printf("Evid.Br   Sif.Zat      Dat.Vrem.Dol  Celija  Kazna\n");
                        ispisiSlog(slog);
                        printf("\n");
					}
                    printf("\n");
					break;
				}
			case 4:
				{
					SLOG dolazak;
					printf("Evid. broj (8 cifara): ");
					scanf("%s", dolazak.evidBroj);
					printf("Sifra zatvorenika (7 karaktera): ");
					scanf("%s", dolazak.sifraZatvorenika);
					printf("Datum i vreme dolaska (dd-mm-YYYY HH:mm): ");
					scanf("%d-%d-%d %d:%d",
						&dolazak.datumVremeDolaska.dan,
						&dolazak.datumVremeDolaska.mesec,
						&dolazak.datumVremeDolaska.godina,
						&dolazak.datumVremeDolaska.sati,
						&dolazak.datumVremeDolaska.minuti);
					printf("Oznaka celije (tacno 5 karaktera): ");
					scanf("%s", dolazak.oznakaCelije);
					printf("Duzina kazne (do 480 meseci): ");
					scanf("%d", &dolazak.duzinaKazne);
					dolazak.deleted = 0;
					dodajSlog(fajl, &dolazak);
                    printf("\n");
					break;
				}
			case 5:
				{
					ispisiSveSlogove(fajl);
                    printf("\n");
					break;
				}
			case 6:
				{
					char evidBroj[8+1];
					printf("Unesite evid. broj sloga koji azurirate: ");
					scanf("%s", evidBroj);
					char oznakaCelije[5+1];
					printf("Unesite oznaku celije: ");
					scanf("%s", oznakaCelije);
					int duzinaKazne;
					printf("Unesite novu duzinu kazne: ");
					scanf("%d", &duzinaKazne);
					azurirajSlog(fajl, evidBroj, oznakaCelije, duzinaKazne);
					printf("\n");
					break;
				}
			case 7:
				{
					char evidBroj[8+1];
					printf("Unesite evid. broj sloga za logicko brisanje: ");
					scanf("%s", evidBroj);
					obrisiSlogLogicki(fajl, evidBroj);
                    printf("\n");
					break;
				}
			case 8:
				{
					char evidBroj[8+1];
					printf("Unesite evid. broj sloga za FIZICKO brisanje: ");
					scanf("%s", evidBroj);
					obrisiSlogFizicki(fajl, evidBroj);
                    printf("\n");
					break;
				}
			case 9:
				{
					reorganizujDatoteku(fajl);
                    printf("\n");
					break;
				}
			case 0:
				{
					//zatvori datoteku
					if (fajl != NULL) {
						fclose(fajl);
					}
					//i zavrsi program
					running = 0;
				}
		}
	}

	return 0;

}



