package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.Resume;

import java.io.*;

public interface StreamSerializer {

    void doWrite(Resume resume, OutputStream os) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}
