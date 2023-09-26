package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serialization.ObjectStreamSerialization;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerialization()));
    }
}