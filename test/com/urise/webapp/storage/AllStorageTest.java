package com.urise.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        ListStorageTest.class,
        SortedArrayStorageTest.class,
        MapResumeStorageTest.class,
        MapUuidStorageTest.class,
        ObjectPathStorageTest.class,
        ObjectFileStorageTest.class,
        XmlPathStorageTest.class,
        DataPathStorageTest.class
})
public class AllStorageTest {
}
