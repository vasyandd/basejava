package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{


   public void clear() {
      Arrays.fill(storage, 0, size, null);
        size = 0;
    }

   public void save(Resume r) {
       if (getIndex(r.getUuid()) != -1) {
           System.out.println("Resume " + r.getUuid() + " already exist");
       } else  if (size == STORAGE_LIMIT ) System.out.println("Storage overflow");
       else {
           storage[size] = r;
           size++;
       }
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index == -1) {
            System.out.println("Resume " + r.getUuid() + " not exist");
        } else storage[index] = r;

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
                storage[i] = storage[size - 1];
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


    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++)
            if (uuid.equals(storage[i].getUuid())) return i;
        return -1;
    }
}
