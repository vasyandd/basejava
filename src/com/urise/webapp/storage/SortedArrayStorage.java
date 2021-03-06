package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage{

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());


    @Override
    protected void fillDeletedElement(int index) {
        int i = size - index - 1;
        if (i > 0) System.arraycopy(storage, index + 1, storage, index, i);
    }

    @Override
    protected void insertElement(Resume r, int index) {
        int indexSearch = - index - 1;
            System.arraycopy(storage, indexSearch, storage, indexSearch + 1, size - indexSearch);
        storage[indexSearch] = r;
    }

    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }
}
