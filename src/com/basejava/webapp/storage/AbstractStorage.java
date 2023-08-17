package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final void update(Resume resume) {
        String uuid = resume.getUuid();
        updateResume(resume, getExistingSearchKey(uuid));
    }

    public final void save(Resume resume) {
        String uuid = resume.getUuid();
        saveResume(resume, getNotExistingSearchKey(uuid));
    }

    public final Resume get(String uuid) {
        return getResume(getExistingSearchKey(uuid));
    }

    public final void delete(String uuid) {
        deleteResume(getExistingSearchKey(uuid));
    }

    protected abstract void updateResume(Resume resume, Object searchKey);

    protected abstract void saveResume(Resume resume, Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void deleteResume(Object searchKey);

    protected abstract Object findSearchKey(String uuid);

    protected abstract boolean isExists(Object searchKey);

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExists(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (isExists(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
