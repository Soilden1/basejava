package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectStreamPathStorage extends AbstractStorage<Path> {

    private final Path directory;

    protected ObjectStreamPathStorage(String dir) {
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
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
//        try {
//            new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(path))).writeObject(resume);
//        } catch (IOException e) {
//            throw new StorageException("Write error", path.toString(), e);
//        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream((Files.newOutputStream(path))))) {
            oos.writeObject(resume);
        } catch (IOException e) {
            throw new StorageException("Write error", path.toString(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("File cannot be saved to " + path, null, e);
        }
        doUpdate(resume, path);
    }

    @Override
    protected Resume doGet(Path path) {
//        try {
//            return (Resume) new ObjectInputStream(new BufferedInputStream(Files.newInputStream(path))).readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            throw new StorageException("Read error", path.toString(), e);
//        }

        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new StorageException("Read error", path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File cannot be deleted to" + path, null, e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getStream().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return (int) getStream().count();
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    protected Stream<Path> getStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Unable to receive path stream", null, e);
        }
    }
}
