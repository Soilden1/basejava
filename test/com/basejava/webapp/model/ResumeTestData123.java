//package com.basejava.webapp.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ResumeTestData123 {
//    private static final Resume resume = new Resume("Григорий Кислин");
//
//    public static void main(String[] args) {
//        insertData();
//        printResume();
//    }
//
//    private static void fillContacts() {
//        contacts.setPhoneNumber(phoneNumber);
//        contacts.setMail(mail);
//        contacts.setGitHubProfile(gitHubProfile);
//    }
//
//    private static void fillTextContent(Resume.TextContent content, String text) {
//        content.setText(text);
//    }
//
//    private static void fillListTextContent(Resume.ListTextContent content, List<String> strings) {
//        content.setTexts(strings);
//    }
//
//    private static void fillBlockContent(Resume.BlockContent content, String title, String text, String boldText, String date) {
//        content.setTitle(title);
//        content.setText(text);
//        content.setBoldText(boldText);
//        content.setDate(date);
//    }
//
//    private static void printResume() {
//        System.out.println(resume.getFullName());
//        System.out.println(contacts);
//        System.out.println(objective);
//        System.out.println(personal);
//        System.out.println(achievement);
//        System.out.println(qualifications);
//        System.out.println(experience);
//        System.out.println(education);
//
//    }
//
//    private static void insertData() {
//        setSectionType();
//        fillContacts(contacts, "+7(921) 855-0482", "gkislin@yandex.ru", "Профиль GitHub");
//        fillTextContent(objective, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
//        fillTextContent(personal, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
//
//        List<String> achievementStrings = new ArrayList<>();
//        achievementStrings.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков");
//        achievementStrings.add("С 2013 года: разработка проектов \"Разработка Web приложения\"");
//        achievementStrings.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.");
//        fillListTextContent(achievement, achievementStrings);
//
//        List<String> qualificationsStrings = new ArrayList<>();
//        qualificationsStrings.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
//        qualificationsStrings.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
//        qualificationsStrings.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
//        fillListTextContent(qualifications, qualificationsStrings);
//
//        fillBlockContent(experience, "Java Online Projects", "Aвтор проектa.",
//                "Создание, организация и проведение Java онлайн проектов и стажировок.", "10/2013 - Сейчас");
//
//        fillBlockContent(education, "Coursera", "\n" +
//                "'Functional Programming Principles in Scala' by Martin Odersky\n", null, "03/2013 - 05/2013");
//    }
//}