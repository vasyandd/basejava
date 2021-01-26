package com.urise.webapp.storage.serializer;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements StreamSerializer{
    @Override
    public void doWrite(Resume r, OutputStream out) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(out)) {
            objectOutputStream.writeObject(r);
        }
    }

    @Override
    public Resume doRead(InputStream in) throws IOException {
        try (ObjectInputStream objIn = new ObjectInputStream(in)) {
            return (Resume) objIn.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Class not found", null, e);
        }
    }
}
