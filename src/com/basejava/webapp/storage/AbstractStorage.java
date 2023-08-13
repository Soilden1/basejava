package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (!isExists(index)) {
            throw new NotExistStorageException(uuid);
        }
        updateResume(resume, index);
    }

    public final void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (isExists(index)) {
            throw new ExistStorageException(uuid);
        }
        saveResume(resume, index);
    }

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (!isExists(index)) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index);
    }

    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (!isExists(index)) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(uuid, index);
    }

    protected abstract void updateResume(Resume resume, int index);

    protected abstract void saveResume(Resume resume, int index);

    protected abstract Resume getResume(int index);

    protected abstract void deleteResume(String uuid, int index);

    protected abstract int findIndex(String uuid);

    protected abstract boolean isExists(int index);
}
