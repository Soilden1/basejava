package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveResume(Resume resume) {
        String uuid = resume.getUuid();
        int index = Math.abs(findIndex(uuid)) - 1;
        System.arraycopy(storage, Math.abs(index), storage, index + 1, countResume - index + 1);
        storage[index] = resume;
        countResume++;
    }

    @Override
    public void deleteResume(String uuid) {
        int index = findIndex(uuid);
        System.arraycopy(storage, index + 1, storage, index, countResume - index - 1);
        countResume--;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResume, searchKey);
    }
}
