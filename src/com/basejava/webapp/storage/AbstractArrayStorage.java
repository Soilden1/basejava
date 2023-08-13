package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResume;

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    @Override
    public final void updateResume(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    public final void saveResume(Resume resume, int index) {
        if (countResume == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else {
            insertElement(resume, index);
            countResume++;
        }
    }

    @Override
    public final Resume getResume(int index) {
        return storage[index];
    }

    @Override
    public final void deleteResume(String uuid, int index) {
        fillDeletedElement(index);
        countResume--;
        storage[countResume] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    public int size() {
        return countResume;
    }

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);

    protected abstract int findIndex(String uuid);

    protected boolean isExists(int index) {
        return index >= 0;
    }
}
