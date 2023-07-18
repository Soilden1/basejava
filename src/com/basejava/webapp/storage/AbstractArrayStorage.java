package com.basejava.webapp.storage;

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
            saveResume(resume);
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
        if (!isExists(findIndex(uuid))) {
            System.out.printf("ERROR: resume with uuid '%s' does not exist%n", uuid);
        } else {
            deleteResume(uuid);
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

    protected abstract void saveResume(Resume resume);

    protected abstract void deleteResume(String uuid);

    protected abstract int findIndex(String uuid);

    protected boolean isExists(int index) {
        return index >= 0;
    }
}
