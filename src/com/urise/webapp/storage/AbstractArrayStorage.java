package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage{
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


    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index == -1) {
            throw new NotExistStorageException(r.getUuid());
        } else storage[index] = r;

    }

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else  if (size == STORAGE_LIMIT ) throw new StorageException("Storage overflow" ,r.getUuid());
        else {
           insertElement(r,index);
           size++;
        }
    }



    public Resume get(String uuid) {
        int i = getIndex(uuid);
        if (i >= 0 ) {
            return storage[i];
        }
       throw new NotExistStorageException(uuid);
    }

    public void delete(String uuid) {
        int i = getIndex(uuid);
        if (i >= 0 ) {
            fillDeletedElement(i);
            storage[size - 1] = null;
            size--;
        } else throw new NotExistStorageException(uuid);
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0 , size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
}
