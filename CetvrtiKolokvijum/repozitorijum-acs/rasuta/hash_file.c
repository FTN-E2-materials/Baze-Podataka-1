#include "hash_file.h"

#include <stdio.h>
#include <stdlib.h>

#define OVERFLOW_FILE_NAME "overflow.dat"

int createHashFile(FILE *pFile) {
    Bucket *emptyContent = calloc(B, sizeof(Bucket));               // calloc inicializuje zauzeti
    fseek(pFile, 0, SEEK_SET);                                      // memorijski prostor nulama
    int retVal = fwrite(emptyContent, sizeof(Bucket), B, pFile);
    free(emptyContent);
    return retVal;
}

int saveBucket(FILE *pFile, Bucket* pBucket, int bucketIndex) {
    fseek(pFile, bucketIndex * sizeof(Bucket), SEEK_SET);
    int retVal = fwrite(pBucket, sizeof(Bucket), 1, pFile) == 1;
    fflush(pFile);
    return retVal;
}

int readBucket(FILE *pFile, Bucket *pBucket, int bucketIndex) {
    fseek(pFile, bucketIndex * sizeof(Bucket), SEEK_SET);
    return fread(pBucket, sizeof(Bucket), 1, pFile) == 1;
}

int readRecordFromSerialFile(FILE *pFile, Record *pRecord) {
    return fread(pRecord, sizeof(Record), 1, pFile);
}

int saveRecordToOverflowFile(FILE *pFile, Record *pRecord) {
    return fwrite(pRecord, sizeof(Record), 1, pFile);
}

int isBucketFull(Bucket bucket) {
    return bucket.records[BUCKET_SIZE - 1].status != EMPTY;
}

int initHashFile(FILE *pFile, FILE *pInputSerialFile) {
    if (feof(pFile)) {
        createHashFile(pFile);
    }

    FILE *pOverflowFile = fopen(OVERFLOW_FILE_NAME, "wb+");
    Record r;

    while(readRecordFromSerialFile(pInputSerialFile, &r)) {
        int h = transformKey(r.key);

        Bucket bucket;
        readBucket(pFile, &bucket, h);
        if (isBucketFull(bucket)) {                             // ukoliko nema mesta u maticnom baketu
            saveRecordToOverflowFile(pOverflowFile, &r);        // slog se smesta u privremenu
        } else {                                                // serijsku datoteku prekoracilaca
            insertRecord(pFile, r);
        }
    }

    fclose(pInputSerialFile);
    rewind(pOverflowFile);

    while(readRecordFromSerialFile(pOverflowFile, &r)) {         // upis prekoracilaca
        insertRecord(pFile, r);
    }

    fclose(pOverflowFile);                                       // zatvaranje i brisanje privremene
    remove(OVERFLOW_FILE_NAME);                                  // serijske datoteke prekoracilaca

    return 0;
}

FindRecordResult findRecord(FILE *pFile, int key) {
    int bucketIndex = transformKey(key);                            // transformacija kljuca u redni broj baketa
    int initialIndex = bucketIndex;                                 // redni broj maticnog baketa
    Bucket bucket;
    FindRecordResult retVal;

    retVal.ind1 = 99;                                               // indikator uspesnosti trazenja
    retVal.ind2 = 0;                                                // indikator postojanja slobodnih lokacija

    while (retVal.ind1 == 99) {
        int q = 0;                                                  // brojac slogova unutar baketa
        readBucket(pFile, &bucket, bucketIndex);                    // citanje baketa
        retVal.bucket = bucket;
        retVal.bucketIndex = bucketIndex;

        while (q < BUCKET_SIZE && retVal.ind1 == 99) {
            Record record = bucket.records[q];
            retVal.record = record;
            retVal.recordIndex = q;

            if (key == record.key && record.status != EMPTY) {
                retVal.ind1 = 0;                                    // uspesno trazenje
            } else if (record.status == EMPTY) {
                retVal.ind1 = 1;                                    // neuspesno trazenje
            } else {
                q++;                                                // nastavak trazenja
            }
        }

        if (q >= BUCKET_SIZE) {
            bucketIndex = nextBucketIndex(bucketIndex);             // prelazak na sledeci baket

            if (bucketIndex == initialIndex) {
                retVal.ind1 = 1;                                    // povratak na maticni baket
                retVal.ind2 = 1;
            }
        }
    }

    return retVal;
}

int alreadyExistsForInsert(FindRecordResult findResult) {
    if (findResult.ind1 == 0) {                             // da li je bilo uspesno trazenje
        #ifdef LOGICAL                                      /* za verziju sa logickim brisanjem */
        if (findResult.record.status == ACTIVE) {           // da li je slog aktivan
            return 1;                                       // slog sa istim kljucem vec postoji
        }
        #else                                               /* za verziju sa fizickim brisanjem */
        return 1;                                           // slog sa istim kljucem vec postoji
        #endif
    }

    return 0;
}

int insertRecord(FILE *pFile, Record record) {
    record.status = ACTIVE;
    FindRecordResult findResult = findRecord(pFile, record.key);

    if (alreadyExistsForInsert(findResult)) {                           // ukoliko slog sa datim kljucem vec postoji
        return -1;
    }

    findResult.bucket.records[findResult.recordIndex] = record;         // upis sloga u baket na mesto gde je neuspesno zavrseno trazenje
                                                                        // ili aktivacija pothodno logicki obrisanog sloga sa istim kljucem

    if(saveBucket(pFile, &findResult.bucket, findResult.bucketIndex)) { // upis baketa u datoteku
        return findResult.bucketIndex;                                  // ukoliko je unos uspesan, povratna vrednost je redni broj baketa
    } else {
        return -2;
    }
}

int alreadyExistsForModify(FindRecordResult findResult) {
    if (findResult.ind1) {                              // da li je bilo neuspesno trazenje
        return 0;                                       // slog nije pronadjen
    }

    #ifdef LOGICAL                                      /* za verziju sa logickim brisanjem */
    if (findResult.record.status == DELETED) {          // da li pronadjeni slog logicki obrisan
        return 0;                                       // slog nije pronadjen
    }
    #endif

    return 1;
}

int modifyRecord(FILE *pFile, Record record) {
    record.status = ACTIVE;
    FindRecordResult findResult = findRecord(pFile, record.key);

    if (!alreadyExistsForModify(findResult)) {                          // ukoliko slog nije pronadjen ili je logicki obrisan
        return -1;
    }

    findResult.bucket.records[findResult.recordIndex] = record;         // upis sloga u baket na mesto gde je pronadjen

    if(saveBucket(pFile, &findResult.bucket, findResult.bucketIndex)) { // upis baketa u datoteku
        return findResult.bucketIndex;                                  // ukoliko je modifikacija uspesna, povratna vrednost je redni broj baketa
    } else {
        return -2;
    }
}

int removeRecordLogically(FILE *pFile, int key) {
    FindRecordResult findResult = findRecord(pFile, key);

    if (findResult.ind1) {
        return -1;                                                      // slog nije pronadjen
    }

    findResult.bucket.records[findResult.recordIndex].status = DELETED; // logicko brisanje sloga

    if(saveBucket(pFile, &findResult.bucket, findResult.bucketIndex)) { // upis baketa u datoteku
        return findResult.bucketIndex;                                  // ukoliko je brisanje uspesno, povratna vrednost je redni broj baketa
    } else {
        return -2;
    }
}

void testCandidate(Record record, int bucketIndex,                      // pomocna funkcija za proveru da li se odredjeni slog moze
                    int targetBucketIndex, int *pFound) {               // prebaciti u ciljni baket kako bi bio blize maticnom baketu
    int m = transformKey(record.key);

    if (bucketIndex > targetBucketIndex) {
        if (m > bucketIndex || m <= targetBucketIndex) {
            *pFound = 1;
        }
    } else {
        if (m > bucketIndex && m <= targetBucketIndex) {
            *pFound = 1;
        }
    }
}

int removeRecordPhysically(FILE *pFile, int key) {
    FindRecordResult findResult = findRecord(pFile, key);

    if (findResult.ind1) {
        return -1;                                                          // slog nije pronadjen
    }

    int q = findResult.recordIndex;                                         // indeks sloga za brisanje u baketu
    Bucket bucket = findResult.bucket;
    int bucketIndex = findResult.bucketIndex;

    int move = 1;                                                           // indikator pomeranja slogova

    while (move) {
        while (q < BUCKET_SIZE - 1 && bucket.records[q].status != EMPTY) {
            bucket.records[q] = bucket.records[q + 1];                      // pomeranje slogova unutar baketa (ulevo)
            q++;
        }

        Bucket targetBucket = bucket;                                       // ciljni baket u koji ce se (eventualno) vrsiti
        int targetBucketIndex = bucketIndex;                                // premestanje slogova prekoracilaca iz narednih baketa

        if (bucket.records[q].status == EMPTY) {
            move = 0;                                                       // baket nije bio pun
        } else {
            int found = 0;

            while (!found && move) {
                if (q == BUCKET_SIZE - 1) {
                    bucketIndex = nextBucketIndex(bucketIndex);             // ukoliko se doslo do kraja baketa
                    readBucket(pFile, &bucket, bucketIndex);                // dobavlja se naredni baket
                    q = -1;
                }

                q++;

                if (bucket.records[q].status != EMPTY && bucketIndex != targetBucketIndex) {
                    testCandidate(bucket.records[q], bucketIndex, targetBucketIndex, &found);
                } else {
                    move = 0;                                               // pomeranje se zaustavlja bilo da se
                }                                                           // naidje na prazan slog ili ciljni baket
            }

            if (found) {
                targetBucket.records[BUCKET_SIZE - 1] = bucket.records[q];  // nadjeni prekoracilac se prebacuje na kraj ciljnog baketa
            } else {
                targetBucket.records[BUCKET_SIZE - 1].status = EMPTY;       // poslednji slog se proglasava praznim
            }
        }

        saveBucket(pFile, &targetBucket, targetBucketIndex);                // ciljni baket se smesta u datoteku
        // ukoliko je nadjen i premesten odgovorajuci prekoracilac,
        // ceo postupak brisanja se ponavlja sad sa njegove prethodne pozicije
        // iz baketa u kojem je nadjen i time se ostavlja prostor da eventualno
        // neki drugi prekoracilac zauzme njegovo prethodno mesto, sto znaci da
        // imamo lancano pomeranje prekoracilaca prema maticnom baketu
    }

    return 0;
}

int removeRecord(FILE *pFile, int key) {
    #ifdef LOGICAL
    return removeRecordLogically(pFile, key);
    #else
    return removeRecordPhysically(pFile, key);
    #endif
}

void printContent(FILE *pFile) {
    int i;
    Bucket bucket;

    for (i = 0; i < B; i++) {
        readBucket(pFile, &bucket, i);
        printf("\n####### BUCKET - %d #######\n", i+1);
        printBucket(bucket);
    }
}
