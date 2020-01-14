#ifndef HASHFILE_H
#define HASHFILE_H

#define LOGICAL

#include <stdio.h>
#include "bucket.h"

int createHashFile(FILE *pFile);
int initHashFile(FILE *pFile, FILE *pFilein);

FindRecordResult findRecord(FILE *pFile, int key);
int insertRecord(FILE *pFile, Record record);
int modifyRecord(FILE *pFile, Record record);
int removeRecord(FILE *pFile, int key);
void printContent(FILE *pFile);

#endif