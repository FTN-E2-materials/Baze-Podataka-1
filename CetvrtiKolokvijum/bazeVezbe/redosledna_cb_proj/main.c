#include "stdio.h"
#include "stdlib.h"

#include "operacije_nad_datotekom.h"

//	pokrenuti sa:
//		gcc *.c -lm && ./a.out asdf.bin
//	(ime fajla "asdf.bin" nije obavezno, moze se
//	naknadno otvoriti izborom opcije 1 iz menija)
//
//	pri kreiranju fajla, uneti naziv kao "asdf.bin"
//	("asdf.bin.p" i "asdf.bin.e" se automatski kreiraju)
//
//	datum/vreme se unosi u formatu "DD-MM-YYYY hh:mm"
//
//	u folderu "bkp" su neki primeri za testiranje;
//	"asdf.bin.p.2" je datoteka sa promenama koja se
//	moze probati kad je vec jedanput iskoriscena
//	"asdf.bin.p" (samo preimenovati "asdf.bin.p.2" u
//	"asdf.bin.p")

int main(int argc, char *argv[]) {


	int running = 1;
	int userInput;

	FILE *datotekaSaPodacima = NULL; //datoteka sa podacima (stara datoteka)
	FILE *datotekaPromena = NULL; //datoteka sa izmenama (datoteka promena)
	FILE *datotekaGresaka = NULL; //datoteka gresaka

	char nazivOtvoreneDatoteke[20];
	//cuvam filename da posle zamenim tmp novu sa starom datotekom
	//(posle redosledne obrade)

	if (argc > 1) {
		strcpy(nazivOtvoreneDatoteke, argv[1]);
		otvoriDatoteku(nazivOtvoreneDatoteke, &datotekaSaPodacima, &datotekaPromena, &datotekaGresaka);
		//redoslednaObrada(&datotekaPromena, &datotekaSaPodacima, &datotekaGresaka);
	}

	while (running) {
		printf("Odaberite opciju:\n");
		printf("1 - Otvaranje datoteke\n");
		printf("2 - Formiranje datoteke\n");
		printf("3 - Pretraga datoteke\n");
		printf("4 - Unos sloga\n");
		printf("5 - Ispis svi slogova\n");
		printf("6 - Azuriranje oznake celije i duzine kazne\n");
		printf("7 - Brisanje sloga\n");
		printf("8 - Pokretanje postupka redosledne obrade\n");
		printf("0 - Izlaz\n");
		if (datotekaSaPodacima == NULL) {
			printf("!!! PAZNJA: datoteka sa podacima jos uvek nije otvorena !!!\n");
		}
		if (datotekaPromena == NULL) {
			printf("!!! PAZNJA: datoteka promena jos uvek nije otvorena !!!\n");
		}
		if (datotekaGresaka == NULL) {
			printf("!!! PAZNJA: datoteka gresaka jos uvek nije otvorena !!!\n");
		}
		scanf("%d", &userInput);
		getc(stdin);

		switch(userInput) {
			case 1:
				{
					//otvori datoteku
					printf("Unesite ime datoteke za otvaranje: ");
					scanf("%s", &nazivOtvoreneDatoteke[0]);
					otvoriDatoteku(nazivOtvoreneDatoteke, &datotekaSaPodacima, &datotekaPromena, &datotekaGresaka);
					printf("\n");
					break;
				}
			case 2:
				{
					//kreiraj datoteku
					char nazivDatotekeSaPodacima[20];
					printf("Unesite ime datoteke za kreiranje: ");
					scanf("%s", nazivDatotekeSaPodacima);
					kreirajDatoteku(nazivDatotekeSaPodacima);
					printf("\n");
					break;
				}
			case 3:
				{
					//pronadji i ispisi slog
					char evidBroj[8+1];
					printf("Unesite evid. broj trazenog prijema: ");
					scanf("%s", evidBroj);
					ispisiSlog(datotekaSaPodacima, evidBroj);
					printf("\n");
					break;
				}
			case 4:
				{
					//unesi novi slog
					char evidBroj[8+1];
					DOLAZAK dolazak;
					printf("Evid. broj (8 cifara): ");
					scanf("%s", evidBroj);
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

					unesiNoviSlog(datotekaPromena, evidBroj, &dolazak);
					printf("Dodat novi slog u datoteku promena (NOVI_SLOG).\n\n");
					break;
				}
			case 5:
				{
					//ispis svih slogova
					ispisiSveSlogove(datotekaSaPodacima, datotekaPromena, datotekaGresaka);
					break;
				}
			case 6:
				{
					//azuriranje oznake celije ili duzine kazne
					char evidBroj[8+1];
					printf("Unesite evid. broj sloga koji azurirate: ");
					scanf("%s", evidBroj);

					char oznakaCelije[5+1];
					printf("Unesite novu oznaku celije: ");
					scanf("%s", oznakaCelije);
					int duzinaKazne;
					printf("Unesite novu duzinu kazne: ");
					scanf("%d", &duzinaKazne);
					azurirajSlog(datotekaPromena, evidBroj, oznakaCelije, duzinaKazne);
					printf("Dodat novi slog u datoteku promena (MODIFIKACIJA).\n\n");
					break;
				}
			case 7:
				{
					//brisanje sloga
					char evidBroj[8+1];
					printf("Unesite evid. broj sloga za brisanje: ");
					scanf("%s", evidBroj);
					obrisiSlog(datotekaPromena, evidBroj);
					printf("Dodat novi slog u datoteku promena (BRISANJE).\n\n");
					break;
				}
			case 8:
				{
					//pokretanje postupka redosledne obrade uz pomoc datoteke promena
					sortirajDatotekuPromena(&datotekaPromena, nazivOtvoreneDatoteke);
					redoslednaObrada(&datotekaPromena, &datotekaSaPodacima, &datotekaGresaka, nazivOtvoreneDatoteke);
					isprazniDatotekuPromena(&datotekaPromena, nazivOtvoreneDatoteke);
					zameniStaruDatotekuPodacimaNovom(&datotekaSaPodacima, nazivOtvoreneDatoteke);
					printf("Zavrsena redosledna obrada.\n\n");
					break;
				}
			case 0:
				{
					//kraj programa
					running = 0;
					if (datotekaSaPodacima != NULL) {
						fclose(datotekaSaPodacima);
					}
					if (datotekaPromena != NULL) {
						fclose(datotekaPromena);
					}
					if (datotekaGresaka != NULL) {
						fclose(datotekaGresaka);
					}
				}
		}
	}

	return 0;

}




