package com.urise.webapp.storage;

public class ObjectStreamStorageTest extends  AbstractStorageTest {
    public ObjectStreamStorageTest() {
        storage = new ObjectStreamStorage(AbstractStorageTest.STORAGE_DIR);
    }
}
