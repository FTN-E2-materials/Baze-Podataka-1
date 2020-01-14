#ifndef OPERACIJE_NAD_DATOTEKOM_H
#define OPERACIJE_NAD_DATOTEKOM_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#include <unistd.h>
#include <sys/types.h>

#include "definicije_struktura_podataka.h"

#define FAJL_N_FILENAME "tmp.bin.n"

void otvoriDatoteku(char *nazivDatotekeSaPodacima, FILE **datotekaSaPodacima, FILE **datotekaPromena, FILE **datotekaGresaka);
void kreirajDatoteku(char *nazivDatotekeSaPodacima);
SLOG_DATOTEKE_SA_PODACIMA *pronadjiSlog(FILE *datotekaSaPodacima, char *evidBroj);
void unesiNoviSlog(FILE *datotekaPromena, char *evidBroj, DOLAZAK *dolazak);
void dodajSlogUDatotekuPromena(FILE *datotekaPromena, SLOG_DATOTEKE_PROMENA *slog);
void ispisiSveSlogove(FILE *datotekaSaPodacima, FILE *datotekaPromena, FILE *datotekaGresaka);
void ispisiSlog(FILE *datotekaSaPodacima, char *evidBroj);
void ispisiDolazak(DOLAZAK *dolazak);
void azurirajSlog(FILE *datotekaPromena, char *evidBroj, char *oznakaCelije, int duzinaKazne);
void obrisiSlog(FILE *datotekaPromena, char *evidBroj);

void sortirajDatotekuPromena(FILE **datotekaPromena, char *nazivDatotekeSaPodacima);
void redoslednaObrada(FILE **datotekaPromena, FILE **datotekaSaPodacima, FILE **datotekaGresaka, char *nazivDatotekeSaPodacima);
void isprazniDatotekuPromena(FILE **datotekaPromena, char *nazivDatotekeSaPodacima);
void zameniStaruDatotekuPodacimaNovom(FILE **datotekaSaPodacima, char *nazivDatotekeSaPodacima);

int citajSlogNaPozicijiIzDatotekeSaPodacima(FILE *datotekaSaPodacima, SLOG_DATOTEKE_SA_PODACIMA *slogDatotekeSaPodacima, int p);
int citajSlogNaPozicijiIzDatotekePromena(FILE *datotekaPromena, SLOG_DATOTEKE_PROMENA *slogDatotekePromena, int p);
void pisiNaPozicijiuUDatotekuSaPodacima(FILE *datotekaSaPodacima, SLOG_DATOTEKE_SA_PODACIMA *slogDatotekeSaPodacima, int p);

#endif
