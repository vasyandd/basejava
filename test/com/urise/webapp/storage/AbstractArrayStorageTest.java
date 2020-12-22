package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

public class AbstractArrayStorageTest extends AbstractStorageTest{
    @Test(expected = StorageException.class)
    public void saveOverflow() {
        for(int i = 4; i <= 10000; i++)
            storage.save(new Resume("Name" + i));
        storage.save(new Resume("Name"));
    }
}
