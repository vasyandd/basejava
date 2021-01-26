package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

public class ObjectPathStorageTest extends  AbstractStorageTest {
    public ObjectPathStorageTest() {
        storage = new PathStorage(AbstractStorageTest.STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer());
    }
}
