package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        storage = new FileStorage(AbstractStorageTest.STORAGE_DIR, new ObjectStreamSerializer());
    }
}
