#ifndef SERIJSKA_H_INCLUDED
#define SERIJSKA_H_INCLUDED
#define FAKTOR_BLOKIRANJA 4

/**
* Struktura Zatvor koja predstavlja osnovne informacije
* o svakom prispelom zatvoreniku u zatvor.
*/
typedef struct zatvor{
    int evidencioniBroj;
    char sifraZatvorenika[7];
    char datumDolasa[15];
    char oznakaCelije[5];
    int duzinaKazne;
} Zatvor;

/**
* Struktura koja reprezentuje nas slog.
*/
typedef struct slog{
    Zatvor zatvor;
    int statusZnaka;
    /* Status znaka uzima sledece vrednosti:
        0- slog logicki aktivan
        1- slog logicki izbrisan
        2- oznaka kraja datoteke
    */
}Slog;

/**
* Struktura Blok koja reprezentuje nas blok odnosno niz slogova.
*/
typedef struct blok{
    Slog slog[FAKTOR_BLOKIRANJA];       //f nam oznacava duzinu bloka
}Blok;


/*
* Kada smo napravili osnovno model, sada slede potpisi funkcija odnosno
* prototipovi funkcija koje cemo kasnije implementirati.
*/

/**
* Funkcija prima pokazivac na string, taj naziv je globalna promenljiva,
* i menja se kroz program i koristice se u vecini narednih funkcija.
*/
void OdabirDatoteke(char **naziv);

void FormiranjeDatoteke(char *naziv);
void PretragaDatoteke(char *naziv);
void UnosNovogSLoga(char *naziv);
void IspisSvihSlogova(char *naziv);
void AzuriranjeSlogova(char *naziv);
void BrisanjeSlogova(char *naziv);
void LogickoBrisanjeSlogova(char **naziv);
void FizickoBrisanjeSlogova(char **naziv);


/**
 *  Funkcija koja proverava da li prosledjeni fajl postoji ili ne.
 *  U slucaju postojanja vraca se 1(true), u suprotnom 0(false).
 */
int FajlPostoji(char *naziv, FILE **f);


void IspisiZatvorenika(Zatvor *zatvor);
void UnosNovogZatvorenika(Zatvor *zatvor);


















#endif // SERIJSKA_H_INCLUDED
