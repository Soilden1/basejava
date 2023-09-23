package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory or is not writable");
        }
     }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {

    }

    @Override
    protected void doSave(Resume resume, Path path) {

    }

    @Override
    protected Resume doGet(Path path) {
        return null;
    }

    @Override
    protected void doDelete(Path path) {

    }

    @Override
    protected List<Resume> doCopyAll() {
        return null;
    }

    @Override
    public int size() {
        return -1;
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return null;
    }

    @Override
    protected boolean isExist(Path path) {
        return false;
    }

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;
}
