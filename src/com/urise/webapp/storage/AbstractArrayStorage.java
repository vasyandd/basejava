package com.urise.webapp.storage;

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
            System.out.println("Resume " + r.getUuid() + " not exist");
        } else storage[index] = r;

    }

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index != -1) {
            System.out.println("Resume " + r.getUuid() + " already exist");
        } else  if (size == STORAGE_LIMIT ) System.out.println("Storage overflow");
        else {
           insertElement(r,index);
           size++;
        }
    }



    public Resume get(String uuid) {
        int i = getIndex(uuid);
        if (i != -1 ) {
            return storage[i];
        }
        System.out.println("Resume " + uuid + " not exist");
        return null;
    }

    public void delete(String uuid) {
        int i = getIndex(uuid);
        if (i != -1 ) {
            fillDeletedElement(i);
            storage[size - 1] = null;
            size--;
        } else System.out.println("ERROR");
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
