package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
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

    public final void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (isExists(index)) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public final void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (isExists(index)) {
            throw new ExistStorageException(uuid);
        } else if (countResume == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", uuid);
        } else {
            insertElement(resume, index);
            countResume++;
        }
    }

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (!isExists(index)) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

   public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (!isExists(index)) {
            throw new NotExistStorageException(uuid);
        } else {
            fillDeletedElement(index);
            countResume--;
            storage[countResume] = null;
        }
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
