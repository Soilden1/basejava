package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int countResume;

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int place = findPlace(uuid);
        if (isExists(uuid, place)) {
            storage[place] = resume;
        }
    }

    public void save(Resume resume) {
        if (countResume == storage.length) {
            System.out.println("ERROR: storage is full");
        } else {
            String uuid = resume.getUuid();
            if (findPlace(uuid) >= 0) {
                System.out.printf("ERROR: resume with uuid '%s' already exists%n", uuid);
            } else {
                storage[countResume++] = resume;
            }
        }
    }

    public Resume get(String uuid) {
        int place = findPlace(uuid);
        return (isExists(uuid, place) ? storage[place] : null);
    }

    public void delete(String uuid) {
        int place = findPlace(uuid);
        if (isExists(uuid, place)) {
            System.arraycopy(storage, place + 1, storage, place, countResume-- - place - 1);
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

    private int findPlace(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isExists(String uuid, int place) {
        if (place < 0) {
            System.out.printf("ERROR: resume with uuid '%s' does not exist%n", uuid);
            return false;
        }
        return true;
    }
}
