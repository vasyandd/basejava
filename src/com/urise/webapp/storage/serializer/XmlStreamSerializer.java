package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer{
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(Resume.class, Organization.class, OrganizationSection.class,
                Link.class, Organization.Position.class, TextSection.class, ListSection.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream out) throws IOException {
        try(Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)){
            xmlParser.marshall(r, writer);
        }
    }

    @Override
    public Resume doRead(InputStream in) throws IOException {
        try(Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
