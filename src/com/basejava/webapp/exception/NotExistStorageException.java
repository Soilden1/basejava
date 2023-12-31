package com.basejava.webapp.exception;

public class NotExistStorageException extends StorageException {

    public NotExistStorageException(String uuid) {
        super("ERROR: resume with uuid '%s' does not exist%n", uuid);
    }
}
