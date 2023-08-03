package com.basejava.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("ERROR: resume with uuid '%s' already exists%n", uuid);
    }
}
