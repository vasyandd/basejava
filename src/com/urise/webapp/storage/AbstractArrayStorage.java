package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage{
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0 , size);
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer)index >= 0;
    }

    @Override
    protected void doDelete(Object searchKey) {
        fillDeletedElement((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage[(Integer)searchKey] = r;
    }


    @Override
    protected void doSave(Resume r, Object searchKey) {
        if (size == STORAGE_LIMIT) throw new StorageException("Overflow exception", r.getUuid());
        insertElement(r, (Integer) searchKey);
        size++;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
}
