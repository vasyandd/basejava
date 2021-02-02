package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.LocalDateAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    private final LocalDateAdapter LOCAL_DATE_ADAPTER = new LocalDateAdapter();

    @Override
    public void doWrite(Resume r, OutputStream out) throws IOException {
        try(DataOutputStream dataOutputStream = new DataOutputStream(out)) {
            dataOutputStream.writeUTF(r.getUuid());
            dataOutputStream.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dataOutputStream.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dataOutputStream.writeUTF(entry.getKey().toString());
                dataOutputStream.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = r.getSections();
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dataOutputStream.writeUTF(entry.getKey().toString());
                sectionWriter(dataOutputStream,entry.getValue());
            }
        }
    }

    @Override
    public Resume doRead(InputStream in) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(in)) {
            Resume resume = new Resume(dataInputStream.readUTF(), dataInputStream.readUTF());
            int contactCount = dataInputStream.readInt();
            for (int i = 0; i < contactCount; i++) {
                resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }
            while (dataInputStream.available() > 0) {
                resume.addSection(SectionType.valueOf(dataInputStream.readUTF()), sectionReader(dataInputStream));
            }
            return resume;
        }
    }

    private Section sectionReader(DataInputStream dataInputStream) throws IOException{
        try {
            Section section;
            int codeSection = dataInputStream.readInt();
            if (codeSection == 1) {
                section = new TextSection(dataInputStream.readUTF());
            } else if (codeSection == 2) {
                List<String> items = new ArrayList<>();
                int itemCount = dataInputStream.readInt();
                for (int i = 0; i < itemCount; i++) {
                    items.add(dataInputStream.readUTF());
                }
                section = new ListSection(items);
            } else {
                List<Organization> organizations = new ArrayList<>();
                int organizationCount = dataInputStream.readInt();
                for (int i = 0; i < organizationCount; i++) {
                    List<Organization.Position> positions = new ArrayList<>();
                    Link link = new Link(dataInputStream.readUTF(), dataInputStream.readUTF());
                    int positionCount = dataInputStream.readInt();
                    for (int j = 0; j < positionCount; j++) {
                        positions.add(new Organization.Position(LOCAL_DATE_ADAPTER.unmarshal(dataInputStream.readUTF()), LOCAL_DATE_ADAPTER.unmarshal(dataInputStream.readUTF())
                                , dataInputStream.readUTF(), dataInputStream.readUTF()));
                    }
                    organizations.add(new Organization(link, positions));
                    }
                    section = new OrganizationSection(organizations);
                }
            return section;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }


    private void sectionWriter(DataOutputStream dataOutputStream, Section section) throws IOException{
        try {
            if (section instanceof TextSection) {
                dataOutputStream.writeInt(1);
                dataOutputStream.writeUTF(((TextSection) section).getContent());
            }
            else if (section instanceof ListSection) {
                dataOutputStream.writeInt(2);
                List<String> items = ((ListSection) section).getItems();
                dataOutputStream.writeInt(items.size());
                for (String item : items) {
                    dataOutputStream.writeUTF(item);
                }
            }
            else if (section instanceof OrganizationSection){
                dataOutputStream.writeInt(3);
                List<Organization> organizations = ((OrganizationSection) section).getOrganizations();
                dataOutputStream.writeInt(organizations.size());
                for (Organization organization : organizations) {
                    Link link = organization.getHomePage();
                    dataOutputStream.writeUTF(link.getName());
                    dataOutputStream.writeUTF(link.getUrl());
                    List<Organization.Position> positions = organization.getPositions();
                    dataOutputStream.writeInt(positions.size());
                    for (Organization.Position position : positions) {
                        dataOutputStream.writeUTF(LOCAL_DATE_ADAPTER.marshal(position.getStartDate()));
                        dataOutputStream.writeUTF(LOCAL_DATE_ADAPTER.marshal(position.getEndDate()));
                        dataOutputStream.writeUTF(position.getTitle());
                        dataOutputStream.writeUTF(position.getDescription());
                    }
                }
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }
}
