package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Arrays;

public class ResumeTestData {

//    public static final File STORAGE_DIR = new File( "C:\\Users\\dimac\\Desktop\\basejava\\storage");

    public static void main(String[] args) {
        System.out.println(createResume("uuid1" , "Григорий Кислин"));
    }

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        fillContacts(resume);
        fillSections(resume);
        return resume;
    }

    private static void fillContacts(Resume resume) {
        resume.getContacts().put(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.getContacts().put(ContactType.SKYPE, "skype:grigory.kislin");
        resume.getContacts().put(ContactType.MAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.LINKEDIN_PROFILE, "");
        resume.getContacts().put(ContactType.GITHUB_PROFILE, "");
        resume.getContacts().put(ContactType.STACKOVERFLOW_PROFILE, "");
        resume.getContacts().put(ContactType.HOME_PAGE, "");
    }

    private static void fillSections(Resume resume) {
        TextSection objective = addTextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.getSections().put(SectionType.OBJECTIVE, objective);

        TextSection personal = addTextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.getSections().put(SectionType.PERSONAL, personal);

        ListSection achievement = addListSection("Организация команды и успешная реализация Java проектов для сторонних заказчиков.",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.");
        resume.getSections().put(SectionType.ACHIEVEMENT, achievement);

        ListSection qualifications = addListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        resume.getSections().put(SectionType.QUALIFICATIONS, qualifications);

        CompanySection experience = addCompanySection(
                addCompany("Java Online Projects", "", LocalDate.of(2013, 10, 1),
                        LocalDate.now(), "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."),
                addCompany("Wrike", "", LocalDate.of(2014, 10, 1),
                        LocalDate.of(2016, 1, 1), "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike"),
                addCompany("RIT Center", "", LocalDate.of(2012, 4, 1),
                        LocalDate.of(2014, 10, 1), "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений."));
        resume.getSections().put(SectionType.EXPERIENCE, experience);

        CompanySection education = addCompanySection(
                addCompany("Coursera", "", LocalDate.of(2013, 3, 1),
                        LocalDate.of(2013, 5, 1),
                        "Functional Programming Principles in Scala by Martin Odersky", ""),
                addCompany("Luxoft", "", LocalDate.of(2011, 3, 1),
                        LocalDate.of(2005, 4, 1),
                        "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", ""),
                addCompany("Siemens AG", "", LocalDate.of(2005, 1, 1),
                        LocalDate.of(2005, 4, 1),
                        "3 месяца обучения мобильным IN сетям (Берлин)", ""));
        resume.getSections().put(SectionType.EDUCATION, education);
    }

    private static TextSection addTextSection(String text) {
        return new TextSection(text);
    }

    private static ListSection addListSection(String... strings) {
        return new ListSection(Arrays.asList(strings));
    }

    private static CompanySection addCompanySection(Company... companies) {
        return new CompanySection(Arrays.asList(companies));
    }

    private static Company addCompany(String name, String website, LocalDate startDate, LocalDate endDate,
                                      String title, String description) {
        Company.Period period = new Company.Period(startDate, endDate, title, description);
        return new Company(name, website, period);
    }
}
