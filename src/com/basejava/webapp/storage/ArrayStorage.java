package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected void saveResume(Resume resume) {
        storage[countResume] = resume;
        countResume++;
    }

    protected void deleteResume(String uuid) {
        int index = findIndex(uuid);
        storage[index] = storage[countResume - 1];
        storage[countResume] = null;
        countResume--;
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
