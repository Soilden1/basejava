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
    public final void updateResume(Resume resume, Object searchKey) {
        storage[(int) searchKey] = resume;
    }

    @Override
    public final void saveResume(Resume resume, Object searchKey) {
        if (countResume == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else {
            insertElement(resume, (int) searchKey);
            countResume++;
        }
    }

    @Override
    public final Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    public final void deleteResume(Object searchKey) {
        fillDeletedElement((int) searchKey);
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

    protected abstract void insertElement(Resume resume, int searchKey);

    protected abstract void fillDeletedElement(int searchKey);

    protected abstract Object findSearchKey(String uuid);

    protected boolean isExists(Object searchKey) {
        return (int) searchKey >= 0;
    }
}
