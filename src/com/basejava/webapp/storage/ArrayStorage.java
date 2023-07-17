package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (isExists(findIndex(uuid))) {
            System.out.printf("ERROR: resume with uuid '%s' already exists%n", uuid);
        } else if (countResume == STORAGE_LIMIT) {
            System.out.println("ERROR: storage is full");
        } else {
            storage[countResume] = resume;
            countResume++;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (!isExists(index)) {
            System.out.printf("ERROR: resume with uuid '%s' does not exist%n", uuid);
        } else {
            storage[index] = storage[countResume - 1];
            storage[countResume] = null;
            countResume--;
        }
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
