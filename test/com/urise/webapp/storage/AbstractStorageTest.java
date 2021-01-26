package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Month;
import java.util.List;

public abstract class AbstractStorageTest {
    Storage storage;
    protected final static File STORAGE_DIR = new File("D:\\progi\\топджава\\basej\\project\\basejava\\storage");
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume R1;
    private static final Resume R2;
    private static final Resume R3;
    static {
        R1 = new Resume(UUID_1, "Name3");
        R2 = new Resume(UUID_2, "Name2");
        R3 = new Resume(UUID_3, "Name1");
        R1.addContact(ContactType.SKYPE, "Vacbkaaaa");
        R1.addContact(ContactType.MAIL, "Kiriluk_v_r@mail.ru");
        R1.addSection(SectionType.ACHIEVEMENT, new TextSection("Лушчий из лучших"));
        R1.addSection(SectionType.EDUCATION, new ListSection("429 school, BGTU VOENMEH"));
        R1.addSection(SectionType.QUALIFICATION, new TextSection("JAVA"));
        R1.addSection(SectionType.OBJECTIVE, new TextSection("engineer"));
        R1.addSection(SectionType.PERSONAL, new ListSection("Замечательный", "Красивый", "GENIUS"));
        R1.addSection(SectionType.EXPERIENCE, new OrganizationSection(new Organization("455VP", "url", new Organization.Position(2019, Month.MARCH, "voenpred", "voenpred"))));
    }
    @Before
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
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
        Resume resume = new Resume(UUID_2, "New Name");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(UUID_2));
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