package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        storage = new PathStorage(AbstractStorageTest.STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer());
    }
}
