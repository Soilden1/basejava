package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int countResume;

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

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (isExists(findIndex(uuid))) {
            System.out.printf("ERROR: resume with uuid '%s' already exists%n", uuid);
        } else if (countResume == STORAGE_LIMIT) {
            System.out.println("ERROR: storage is full");
        } else {
            storage[countResume] = resume;
            countResume++;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExists(index)) {
            return storage[index];
        }
        System.out.printf("ERROR: resume with uuid '%s' does not exist%n", uuid);
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (!isExists(index)) {
            System.out.printf("ERROR: resume with uuid '%s' does not exist%n", uuid);
        } else {
            storage[index] = storage[countResume - 1];
            storage[countResume] = null;
            countResume--;
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

    private int findIndex(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isExists(int index) {
        return index >= 0;
    }
}
