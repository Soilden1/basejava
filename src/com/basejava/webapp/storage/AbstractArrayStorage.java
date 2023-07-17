package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResume;

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExists(index)) {
            return storage[index];
        }
        System.out.printf("ERROR: resume with uuid '%s' does not exist%n", uuid);
        return null;
    }

    protected abstract int findIndex(String uuid);

    public int size() {
        return countResume;
    }

    protected boolean isExists(int index) {
        return index >= 0;
    }
}
