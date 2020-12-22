package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public abstract class AbstractStorageTest {
    Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1, "Name3"));
        storage.save(new Resume(UUID_2, "Name2"));
        storage.save(new Resume(UUID_3, "Name1"));
    }
    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
    }

    @Test
    public void save() {
        storage.save(new Resume("fdsfsdf", "gg"));
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void get() {
        Resume resume = new Resume("uuid1", "Name3");
        Assert.assertEquals(resume, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid1");
        Assert.assertEquals(2, storage.size());
        storage.get("uuid1");
    }

    @Test
    public void getAllSorted() {
        List<Resume> resumes = storage.getAllSorted();
        Assert.assertEquals(resumes.size(), storage.size());
        Assert.assertEquals(resumes.get(0), storage.get("uuid3"));
    }

}