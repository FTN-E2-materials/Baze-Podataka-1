#define FBLOKIRANJA_S 3
#define FBLOKIRANJA_P 3
#define OZNAKA_KRAJA_DATOTEKE "99999999"

typedef struct DateTimeSt {
	int dan;
	int mesec;
	int godina;
	int sati;
	int minuti;
} DATETIME;

typedef enum operacija {
	OP_NOVI_SLOG,
	OP_MODIFIKACIJA,
	OP_BRISANJE
} OPERACIJA;

typedef struct DolazakSt {
	char sifraZatvorenika[7+1];
	DATETIME datumVremeDolaska;
	char oznakaCelije[5+1];
	int duzinaKazne;
} DOLAZAK;

typedef struct SlogDatotekeSaPodacimaSt {
	char evidBroj[8+1];		//kljuc sloga
	DOLAZAK dolazak;		//sadrzaj sloga
} SLOG_DATOTEKE_SA_PODACIMA;

typedef struct SlogDatotekePromenaSt {
	char evidBroj[8+1];
	DOLAZAK dolazak;
	OPERACIJA op;
} SLOG_DATOTEKE_PROMENA;

typedef struct BlokDatotekeSaPodacimaSt {
	SLOG_DATOTEKE_SA_PODACIMA slogovi[FBLOKIRANJA_S];
} BLOK_DATOTEKE_SA_PODACIMA;

typedef struct BlokDatotekePromenaSt {
	SLOG_DATOTEKE_PROMENA slogovi[FBLOKIRANJA_P];
} BLOK_DATOTEKE_PROMENA;
