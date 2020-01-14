#ifndef BUCKET_H
#define BUCKET_H

#define B 7             // broj baketa
#define STEP 1          // fiksan korak
#define BUCKET_SIZE 3   // faktor baketiranja

#define WITH_HEADER 1
#define WITHOUT_HEADER 0
#define WITH_KEY 1
#define WITHOUT_KEY 0

#define RECORD_FOUND 0

#define CODE_LEN 8
#define DATE_LEN 11

typedef enum { EMPTY = 0, ACTIVE, DELETED } RECORD_STATUS;

typedef struct {
    int key;
    char code[CODE_LEN];
    char date[DATE_LEN];
    RECORD_STATUS status; 
} Record;

typedef struct {
    Record records[BUCKET_SIZE];
} Bucket;

typedef struct {
    int ind1;           // indikator uspesnosti trazenja, 0 - uspesno, 1 - neuspesno
    int ind2;           // indikator postojanja slobodnih lokacija, 0 - nema, 1 - ima
    Bucket bucket;      
    Record record;
    int bucketIndex;    // indeks baketa sa nadjenim slogom
    int recordIndex;    // indeks sloga u baketu
} FindRecordResult;

int transformKey(int key);
int nextBucketIndex(int currentIndex);
void printRecord(Record record, int header);
void printBucket(Bucket bucket);
Record scanRecord(int withKey);
int scanKey();

#endif