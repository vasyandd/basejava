package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {
   void doWrite(Resume r, OutputStream out) throws IOException;
   Resume doRead(InputStream in) throws IOException;
}
