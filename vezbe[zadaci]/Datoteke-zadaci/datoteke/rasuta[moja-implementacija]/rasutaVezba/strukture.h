#define b 3                 // faktor baketiranja je 3 a broj baketa je 7
#define B 7
#define FBLOKIRANJA_S 3
#define OZNAKA_KRAJA_DATOTEKE "********"
#define KORAK 1             // korak za trazenje narednog bloka



/* Svaki slog predstavlja jedan dolazak. */
typedef struct Slog {
	char evidBroj[8+1];     //koristi se kao kljuc sloga
	char sifraZatvorenika[7+1];
	char datumDolaska[20];
	char oznakaCelije[5+1];
	int duzinaKazne;
	int deleted;            // 0 - EMPTY, 1 - ACTIV, 2 - DELETED kod RASUTE
} SLOG;

typedef struct Baket {
	SLOG slogovi[b];
} BAKET;


/* Struktura za iscitavanje iz serijske datoteke prilikom upisa u rasutu. */
typedef struct DateTime {
	int dan;
	int mesec;
	int godina;
	int sati;
	int minuti;
} DATETIME;


typedef struct Slogss {
	char evidBroj[8+1];
	char sifraZatvorenika[7+1];
	DATETIME datumVremeDolaska;
	char oznakaCelije[5+1];
	int duzinaKazne;
	int deleted;            // 0 - neobrisan, 1 - obrisan
} SLOG_SS;

typedef struct Blokss {
    SLOG_SS slogovi[FBLOKIRANJA_S];
} BLOK_SS;                  //stara serijska, tj ona iz koje iscitavamo
