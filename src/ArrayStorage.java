import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int countResume;

    void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    void save(Resume resume) {
        storage[countResume++] = resume;
    }

    Resume get(String uuid) {
        int place = findPlace(uuid);
        return (place >= 0 ? storage[place] : null);
    }

    void delete(String uuid) {
        int place = findPlace(uuid);
        if (place >= 0) {
            System.arraycopy(storage, place + 1, storage, place, countResume-- - place - 1);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    int size() {
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
}
