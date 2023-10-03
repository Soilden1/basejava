package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(entry.getValue().toString());
                    case ACHIEVEMENT, QUALIFICATIONS -> writeListSection(dos, (ListSection) entry.getValue());
                    case EXPERIENCE, EDUCATION -> writeCompanySection(dos, (CompanySection) entry.getValue());
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();

            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(sectionType, readListSection(dis));
                    case EXPERIENCE, EDUCATION -> resume.addSection(sectionType, readCompanySection(dis));
                }
            }
            return resume;
        }
    }

    private void writeListSection(DataOutputStream dos, ListSection listSection) throws IOException {
        List<String> items = listSection.getItems();
        dos.writeInt(items.size());
        for (String item : items) {
            dos.writeUTF(item);
        }
    }

    private ListSection readListSection(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            items.add(dis.readUTF());
        }
        return new ListSection(items);
    }

    private void writeCompanySection(DataOutputStream dos, CompanySection companySection) throws IOException {
        List<Company> companies = companySection.getCompanies();
        dos.writeInt(companies.size());
        for (Company company : companies) {
            writeCompany(dos, company);
        }
    }

    private CompanySection readCompanySection(DataInputStream dis) throws IOException {
        List<Company> companies = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            companies.add(readCompany(dis));
        }
        return new CompanySection(companies);
    }

    private void writeCompany(DataOutputStream dos, Company company) throws IOException {
        Link link = company.getHomePage();
        dos.writeUTF(link.getName());
        dos.writeUTF(link.getUrl());

        List<Company.Period> periods = company.getPeriods();
        dos.writeInt(periods.size());
        for (Company.Period period : periods) {
            writePeriod(dos, period);
        }
    }

    private Company readCompany(DataInputStream dis) throws IOException {
        Link link = new Link(dis.readUTF(), dis.readUTF());

        List<Company.Period> periods = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            periods.add(readPeriod(dis));
        }
        return new Company(link, periods);
    }

    private void writePeriod(DataOutputStream dos, Company.Period period) throws IOException {
        writeLocalDate(dos, period.getStartDate());
        writeLocalDate(dos, period.getEndDate());
        dos.writeUTF(period.getTitle());
        dos.writeUTF(period.getDescription());
    }

    private Company.Period readPeriod(DataInputStream dis) throws IOException {
        LocalDate startDate = readLocalDate(dis);
        LocalDate endDate = readLocalDate(dis);
        String title = dis.readUTF();
        String description = dis.readUTF();
        return new Company.Period(startDate, endDate, title, description);
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
}
