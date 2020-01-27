#include "metode.h"

FILE *otvoriDatoteku(char *imefajla) {
	FILE *fajl = fopen(imefajla, "rb+");
	if (fajl == NULL) {
		printf("Doslo je do greske! Moguce da datoteka \"%s\" ne postoji.\n", imefajla);
	} else {
		printf("Datoteka \"%s\" otvorena.\n", imefajla);
	}
	return fajl;
}

void kreirajRasutuDatoteku(char *imefajla){
    FILE *fajl = fopen(imefajla, "wb");
	if (fajl == NULL) {
		printf("Doslo je do greske prilikom kreiranja datoteke \"%s\"!\n", imefajla);
	} else {
		//Kreiramo inicijalne bakete postavljene na pocetne vrednosti status flega.
        BAKET baketi[B];
        for(int i = 0; i < B; i++){                 // pravimo B(7 npr) baketa
            fwrite(&baketi[i],sizeof(BAKET),1,fajl);
        }

        fseek(fajl,0,SEEK_SET);
        for(int i = 0; i<B;i++){
            fread(&baketi[i],sizeof(BAKET),1,fajl);
            for(int j = 0; j < b; j++){
                baketi[i].slogovi[j].duzinaKazne = 0;
                baketi[i].slogovi[j].deleted = 0;
                strcpy(baketi[i].slogovi[j].evidBroj,"-");
                strcpy(baketi[i].slogovi[j].sifraZatvorenika,"-");
                strcpy(baketi[i].slogovi[j].datumDolaska,"-");
                strcpy(baketi[i].slogovi[j].oznakaCelije,"-");

            }
            fseek(fajl,-sizeof(BAKET),SEEK_CUR);
            fwrite(&baketi[i],sizeof(BAKET),1,fajl);

        }




		printf("Datoteka \"%s\" uspesno kreirana.\n", imefajla);
		fclose(fajl);
	}

}

void ispisRasute(FILE *fajl){
    if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

    BAKET baketi[B];
    fseek(fajl,0,SEEK_SET);

    printf("B S\tevidBr.  sifZat. datDol. oznCel. duzKaz. status\t\n");
    for(int i = 0; i < B; i++){
        fread(&baketi[i],sizeof(BAKET),1,fajl);
        for(int j = 0; j < b; j++){
            printf("%d %d\t",i,j);
            printf("%s\t %s\t %s\t %s\t %d\t %d\t\n",baketi[i].slogovi[j].evidBroj,baketi[i].slogovi[j].sifraZatvorenika,baketi[i].slogovi[j].datumDolaska,baketi[i].slogovi[j].oznakaCelije,baketi[i].slogovi[j].duzinaKazne,baketi[i].slogovi[j].deleted);

        }
        //printf("\n\n");
    }

}

/**
    Metoda koja se poziva u prebacivanju iz serijske u rasutu datoteku
    Ona preko rekurzije pronalazi sledeci slobodan baket i vraca njegov
    indeks tj. vraca broj baketa.
*/
int nadjiSlobodanBaket(int adresaPunog,FILE *fajlRasuta){
    int adresaSledeceg = adresaPunog;
    /* Ucitam niz baketa prvo */
    BAKET baketi[B];
    fseek(fajlRasuta,0,SEEK_SET);
    for(int i = 0; i < B; i++){
        fread(&baketi[i],sizeof(BAKET),1,fajlRasuta);
    }
    // po formuli adresa = (trenutaAdresa + korak)%B
    adresaSledeceg = (adresaPunog + KORAK)%B;
    if(baketi[adresaSledeceg].slogovi[b-1].deleted != 0){
        // to znaci da je trenutni blok pun i da treba da idemo na proveru za sledeci
        adresaSledeceg = nadjiSlobodanBaket(adresaSledeceg,fajlRasuta);
    }
    return adresaSledeceg;
}

/**
    Metoda koja vrsi prebacivanje serijske datoteku u rasutu.
*/
void prebaciSerijskaRasuta(FILE *fajlSerijska,FILE *fajlRasuta){
    if (fajlSerijska == NULL || fajlRasuta == NULL ) {
		printf("SERIJSKA ili RASUTA datoteka nije otvorena!\n");
		return;
	}

	/* Ucitam niz baketa prvo */
    BAKET baketi[B];
    fseek(fajlRasuta,0,SEEK_SET);
    for(int i = 0; i < B; i++){
        fread(&baketi[i],sizeof(BAKET),1,fajlRasuta);
    }

	BLOK_SS blok;
    /* Iscitavanje iz serijske i upis u rasutu */
    fseek(fajlSerijska,0,SEEK_SET);
    while(fread(&blok,sizeof(BLOK_SS),1,fajlSerijska)){         // prolazak kroz svaki blok serijske datoteke
        for(int i = 0; i < FBLOKIRANJA_S; i++){                 // prolazak kroz svaki slog serijske datoteke
            if (strcmp(blok.slogovi[i].evidBroj, OZNAKA_KRAJA_DATOTEKE) == 0) {
                return;                                         // ovo radimo kako bi dodali samo "popunjene" slogove u blokovima
            }

            /* Sada odredim u koji baket dodajem slog. */
            int id = atoi(blok.slogovi[i].evidBroj);
            int adresa = id%B;                                  /* METODA OSTATKA PRI DELJENJU */
            // printf("evidBroju %d je maticni blok %d\n",id,adresa);
            for(int j = 0; j < b; j++){
                if (baketi[adresa].slogovi[b-1].deleted != 0){      // ako nije 0 - EMPTY
                    /*
                        U maticnom baketu nema vise mesta, sto iziskuje potragu za sledecim
                        baketom. Slog koji ne moze stati u maticni baket se naziva PREKORACILAC.

                    */
                    // printf("Imamo prekoracioca sa evidBr: %s\n",blok.slogovi[i].evidBroj);
                    // printf("Nije stao u svoj maticni blok %d",adresa);
                    adresa = nadjiSlobodanBaket(adresa,fajlRasuta);
                    // printf(" ali je nasao svoje mesto u bloku %d\n",adresa);
                }
                if(baketi[adresa].slogovi[j].deleted == 0){         // 0 - EMPTY, ako je empty dodaj tu SLOG
                    baketi[adresa].slogovi[j].deleted = 1;          // 1- ACTIV
                    /*
                        Moglo bi preko memcpy(...) da su slogovi u rasutoj i serijskoj isti
                        ali posto se polja slogova razlikuju u serijskoj i rasutoj, vrsimo
                        kopiranje samo korespodentnih polja.
                    */
                    baketi[adresa].slogovi[j].duzinaKazne = blok.slogovi[i].duzinaKazne;
                    strcpy(baketi[adresa].slogovi[j].evidBroj,blok.slogovi[i].evidBroj);
                    strcpy(baketi[adresa].slogovi[j].sifraZatvorenika,blok.slogovi[i].sifraZatvorenika);
                    strcpy(baketi[adresa].slogovi[j].datumDolaska," - ");       // datum je iz serijske u strukturi tkd preskocicu to sad
                    strcpy(baketi[adresa].slogovi[j].oznakaCelije,blok.slogovi[i].oznakaCelije);
                    break; // izlazimo iz fora kako bi samo j-tom slogu promenio status a ne svakom slogu u baketu
                }

            }
            /*
                Radimo potom DODAVANJE u RASUTU datoteku. tj vrsimo
                upisivanje podataka u rasutu datoteku.

                Pokazivac pomeramo od pocetka za adresa*sizeof(BAKET)
                kako bi mogli na pravo mesto upisati podatke u rasutu
                datoteku.
            */
            fseek(fajlRasuta,adresa*sizeof(BAKET),SEEK_SET);
            fwrite(&baketi[adresa],sizeof(BAKET),1,fajlRasuta);


        }
    }
}
