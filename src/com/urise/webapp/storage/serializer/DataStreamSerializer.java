package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream out) throws IOException {
        try(DataOutputStream dataOutputStream = new DataOutputStream(out)) {
            dataOutputStream.writeUTF(r.getUuid());
            dataOutputStream.writeUTF(r.getFullName());
            writeCollection(dataOutputStream, r.getContacts().entrySet(), entry -> {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            } );
            writeCollection(dataOutputStream, r.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dataOutputStream.writeUTF(type.name());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dataOutputStream.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        writeCollection(dataOutputStream, ((ListSection) section).getItems(), dataOutputStream::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dataOutputStream, ((OrganizationSection) section).getOrganizations(), organization -> {
                            dataOutputStream.writeUTF(organization.getHomePage().getName());
                            dataOutputStream.writeUTF(organization.getHomePage().getUrl());
                            writeCollection(dataOutputStream, organization.getPositions(), position -> {
                                writeLocalDate(dataOutputStream, position.getStartDate());
                                writeLocalDate(dataOutputStream, position.getEndDate());
                                dataOutputStream.writeUTF(position.getTitle());
                                dataOutputStream.writeUTF(position.getDescription());
                            });
                        });
                        break;
                }
            } );
        }
    }

    @Override
    public Resume doRead(InputStream in) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(in)) {
            Resume resume = new Resume(dataInputStream.readUTF(), dataInputStream.readUTF());
            readItems(dataInputStream, () -> resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF()));
            readItems(dataInputStream, () -> {
                SectionType type = SectionType.valueOf(dataInputStream.readUTF());
                resume.addSection(type, readSection(dataInputStream, type));
            });
            return resume;
    }
    }

    private Section readSection(DataInputStream dataInputStream, SectionType type) throws IOException{
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dataInputStream.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATION:
                return new ListSection(readList(dataInputStream, dataInputStream::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(
                        readList(dataInputStream, () -> new Organization(
                        new Link(dataInputStream.readUTF(), dataInputStream.readUTF()),
                                readList(dataInputStream, () -> new Organization.Position(
                                        readLocalDate(dataInputStream), readLocalDate(dataInputStream), dataInputStream.readUTF(), dataInputStream.readUTF())))));
            default: throw new IllegalStateException();
        }

    }

    private <T> List<T> readList(DataInputStream dataInputStream, ElementReader<T> elementReader) throws IOException {
        int size = dataInputStream.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(elementReader.read());
        }
        return list;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }


    private interface ElementWriter <T> {
        void write(T element) throws IOException;
    }
    private interface ElementProcessor {
        void process()throws IOException;
    }

    private void readItems(DataInputStream dataInputStream, ElementProcessor elementProcessor) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            elementProcessor.process();
        }
    }
    private <T> void writeCollection(DataOutputStream dataOutputStream, Collection<T> collection, ElementWriter<T> elementWriter) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T t : collection) {
            elementWriter.write(t);
        }
    }

    private void writeLocalDate(DataOutputStream dataOutputStream, LocalDate ld) throws IOException {
        dataOutputStream.write(ld.getYear());
        dataOutputStream.write(ld.getMonth().getValue());
    }

    private LocalDate readLocalDate(DataInputStream dataInputStream) throws IOException{
        return LocalDate.of(dataInputStream.readInt(), dataInputStream.readInt(), 1);
    }


}
