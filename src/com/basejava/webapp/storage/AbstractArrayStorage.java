package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResume;

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (isExists(index)) {
            storage[index] = resume;
        } else {
            System.out.printf("ERROR: resume with uuid '%s' does not exist%n", uuid);
        }
    }

    public abstract void save(Resume resume);

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExists(index)) {
            return storage[index];
        }
        System.out.printf("ERROR: resume with uuid '%s' does not exist%n", uuid);
        return null;
    }

    public abstract void delete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    public int size() {
        return countResume;
    }

    protected abstract int findIndex(String uuid);

    protected boolean isExists(int index) {
        return index >= 0;
    }
}
