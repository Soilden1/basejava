package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.basejava.webapp.model.ResumeTestData.createResume;
import static com.basejava.webapp.storage.AbstractStorage.RESUME_COMPARATOR;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = new File( "storage");

    private static final String UUID_1 = "uuid1";
    private static final String FULL_NAME_1 = "Aleksey Petrov";
    private static final Resume RESUME_1 = createResume(UUID_1, FULL_NAME_1);

    private static final String UUID_2 = "uuid2";
    private static final String FULL_NAME_2 = "Petr Ivanov";
    private static final Resume RESUME_2 = createResume(UUID_2, FULL_NAME_2);

    private static final String UUID_3 = "uuid3";
    private static final String FULL_NAME_3 = "Konstantin Aleksandrov";
    private static final Resume RESUME_3 = createResume(UUID_3, FULL_NAME_3);

    private static final String UUID_4 = "uuid4";
    private static final String FULL_NAME_4 = "Aleksandr Volkov";
    private static final Resume RESUME_4 = createResume(UUID_4, FULL_NAME_4);

    private static final String UUID_5 = "uuid5";
    private static final String COPY_FULL_NAME_3 = FULL_NAME_3;
    private static final Resume RESUME_5 = createResume(UUID_5, COPY_FULL_NAME_3);

    private static final String UUID_NOT_EXIST = "dummy";
    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Resume[] expected = {};
        Assert.assertArrayEquals(expected, storage.getAllSorted().toArray(new Resume[0]));
    }

    @Test
    public void update() {
        Resume newResume = createResume(UUID_1, "New Name");
        storage.update(newResume);
        Assert.assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = createResume(UUID_NOT_EXIST, "dummy");
        storage.update(resume);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = Arrays.asList(RESUME_5, RESUME_1, RESUME_2, RESUME_3);
        Assert.assertEquals(4, expected.size());
        storage.save(RESUME_5);
        expected.sort(RESUME_COMPARATOR);
        Assert.assertEquals(expected, storage.getAllSorted());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}