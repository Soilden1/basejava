package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected void insertElement(Resume resume, int searchKey) {
        storage[countResume] = resume;
    }

    protected void fillDeletedElement(int searchKey) {
        storage[searchKey] = storage[countResume - 1];
    }

    protected Object findSearchKey(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
