package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        storage = new PathStorage(STORAGE_DIR.getAbsolutePath(),new XmlStreamSerializer());
    }
}
