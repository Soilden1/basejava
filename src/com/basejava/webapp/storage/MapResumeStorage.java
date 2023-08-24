package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected Map<Resume, Resume> storage = new LinkedHashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.put((Resume) searchKey, resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((Resume) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((Resume) searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listStorage = Arrays.asList(storage.values().toArray(new Resume[0]));
        listStorage.sort(RESUME_COMPARATOR);
        return listStorage;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume findSearchKey(String uuid) {
        for (Map.Entry<Resume, Resume> entry : storage.entrySet()) {
            if (entry.getKey().getUuid().equals(uuid)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((Resume) searchKey);
    }
}
