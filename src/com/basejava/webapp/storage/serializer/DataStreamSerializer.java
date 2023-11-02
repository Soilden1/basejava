package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeWithException(resume.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeWithException(resume.getSections().entrySet(), dos, entry -> {
                SectionType sectionType = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(section.toString());
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            writeWithException(((ListSection) section).getItems(), dos, dos::writeUTF);
                    case EXPERIENCE, EDUCATION ->
                            writeWithException(((CompanySection) section).getCompanies(), dos, company -> {
                                dos.writeUTF(company.getHomePage().getName());
                                dos.writeUTF(company.getHomePage().getUrl());
                                writeWithException(company.getPeriods(), dos, period -> {
                                    writeLocalDate(dos, period.getStartDate());
                                    writeLocalDate(dos, period.getEndDate());
                                    dos.writeUTF(period.getTitle());
                                    dos.writeUTF(period.getDescription());
                                });
                            });
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();

            Resume resume = new Resume(uuid, fullName);
            readResumeElements(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readResumeElements(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        return switch (sectionType) {
            case OBJECTIVE, PERSONAL -> new TextSection(dis.readUTF());
            case ACHIEVEMENT, QUALIFICATIONS -> new ListSection(readWithException(dis, dis::readUTF));
            case EXPERIENCE, EDUCATION -> new CompanySection(readWithException(dis, () ->
                    new Company(new Link(dis.readUTF(), dis.readUTF()), readWithException(dis, () ->
                            new Company.Period(readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF())))));
        };
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
        dos.writeInt(localDate.getDayOfMonth());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        int year = dis.readInt();
        int month = dis.readInt();
        int day = dis.readInt();
        return LocalDate.of(year, month, day);
    }

    @FunctionalInterface
    private interface SectionWriter<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    private interface SectionReader<T> {
        T read() throws IOException;
    }

    @FunctionalInterface
    private interface ElementIntoObjectReader {
        void readIntoObject() throws IOException;
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, SectionWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection)
            writer.write(element);
    }

    private <T> List<T> readWithException(DataInputStream dis, SectionReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private void readResumeElements(DataInputStream dis, ElementIntoObjectReader rior) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            rior.readIntoObject();
        }
    }
}
