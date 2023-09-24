package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.io.*;

public interface SerializationStrategy {

    void doWrite(Resume resume, OutputStream os) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}
