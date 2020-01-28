#ifndef METODE_H_INCLUDED
#define METODE_H_INCLUDED


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "strukture.h"

FILE *otvoriDatoteku(char *imefajla);
void kreirajRasutuDatoteku(char *imefajla);
void ispisRasute(FILE *fajl);
void prebaciSerijskaRasuta(FILE *fajlSerijska,FILE *fajlRasuta);

#endif // METODE_H_INCLUDED
