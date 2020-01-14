#include "bucket.h"

#include <stdio.h>

int transformKey(int id) {
    return id % B;
}

int nextBucketIndex(int currentIndex) {
    return (currentIndex + STEP) % B;
}

void printHeader() {
    printf("status \t key \t code \t date\n");
}

void printRecord(Record record, int header) {
    if (header) printHeader();
    printf("%d \t %d \t %s \t %s\n", record.status, record.key, record.code, record.date);
}

void printBucket(Bucket bucket) {
    int i;
    printHeader();
    for (i = 0; i < BUCKET_SIZE; i++) {
        printRecord(bucket.records[i], WITHOUT_HEADER);
    }
}

Record scanRecord(int withKey) {
    Record record;

    printf("\nUnesite slog: \n");
    
    if (withKey) {
        printf("key = ");
        scanf("%d", &record.key);
    }

    printf("code = ");
    scanf("%s", record.code);
    printf("date = ");
    scanf("%s", record.date);

    return record;
}

int scanKey() {
    int key;
    printf("key = ");
    scanf("%d", &key);
    return key;
}