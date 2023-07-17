package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (isExists(index)) {
            System.out.printf("ERROR: resume with uuid '%s' already exists%n", uuid);
        } else if (countResume == STORAGE_LIMIT) {
            System.out.println("ERROR: storage is full");
        } else {
            index = Math.abs(index) - 1;
            System.arraycopy(storage, Math.abs(index), storage, index + 1, countResume - index + 1);
            storage[index] = resume;
            countResume++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (!isExists(index)) {
            System.out.printf("ERROR: resume with uuid '%s' does not exist%n", uuid);
        } else {
            System.arraycopy(storage, index + 1, storage, index, countResume - index - 1);
            countResume--;
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResume, searchKey);
    }
}
