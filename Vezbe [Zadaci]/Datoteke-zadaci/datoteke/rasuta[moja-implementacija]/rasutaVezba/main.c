#include <stdio.h>
#include <stdlib.h>

#include "metode.h"


int main()
{
    int running = 1;
	int userInput;

	FILE *fajlRasuta = NULL;
    FILE *fajlSerijska = NULL;
	while (running) {
		printf("Odaberite opciju:\n");
		printf("1 - Otvaranje datoteka\n");
		printf("2 - Kreiraj rasutu\n");
		printf("3 - Ispisi rasutu\n");
		printf("4 - Konvertuj iz serijske u rasutu\n");
		printf("0 - Izlaz\n");
		if (fajlRasuta == NULL) {
			printf("!!! PAZNJA: RASUTA datoteka jos uvek nije otvorena !!!\n");
		}
		if (fajlSerijska == NULL) {
			printf("!!! PAZNJA: SERIJSKA datoteka jos uvek nije otvorena !!!\n");
		}
		scanf("%d", &userInput);

		switch(userInput) {
			case 1:
				{
                    char filename[20];
					printf("Unesite ime RASUTE datoteke za otvaranje: ");
					scanf("%s", filename);
					fajlRasuta = otvoriDatoteku(filename);

                    char filename1[20];
					printf("Unesite ime SERIJSKE datoteke za otvaranje: ");
					scanf("%s", filename1);
					fajlSerijska = otvoriDatoteku(filename1);

					printf("\n");
					break;
				}
			case 2:
				{
                    char filename[20];
					printf("Unesite ime RASUTE datoteke za kreiranje: ");
					scanf("%s", filename);
					kreirajRasutuDatoteku(filename);

					printf("\n");
					break;
				}
			case 3:
				{
                    ispisRasute(fajlRasuta);
					printf("\n");
					break;
				}
			case 4:
				{
                    prebaciSerijskaRasuta(fajlSerijska,fajlRasuta);
					printf("\n");
					break;
				}
			case 0:
				{
					running = 0;
                    fclose(fajlRasuta);
                    fclose(fajlSerijska);

				}
		}
	}
}
