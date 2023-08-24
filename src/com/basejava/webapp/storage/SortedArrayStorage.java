package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
/*    private static class resumeComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    }*/

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    protected void insertElement(Resume resume, int index) {
        int insertIndex = Math.abs(index) - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, countResume - insertIndex);
        storage[insertIndex] = resume;
    }

    @Override
    protected void fillDeletedElement(int index) {
        int numMoved = countResume - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, countResume, searchKey, RESUME_COMPARATOR);
    }
}
