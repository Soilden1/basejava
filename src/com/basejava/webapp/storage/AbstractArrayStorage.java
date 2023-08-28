package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResume;

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    @Override
    public final void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    public final void doSave(Resume resume, Integer index) {
        if (countResume == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else {
            insertElement(resume, index);
            countResume++;
        }
    }

    @Override
    public final Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    public final void doDelete(Integer index) {
        fillDeletedElement(index);
        countResume--;
        storage[countResume] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, countResume));
    }

    public int size() {
        return countResume;
    }

    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);

    protected abstract Integer findSearchKey(String uuid);
}
