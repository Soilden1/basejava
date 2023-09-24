package com.basejava.webapp;

import java.io.File;

public class RecursiveDirectoryTraversal {

    private static final String ROOT_PATH = "src/com/basejava/webapp";
    private static final String INDENT = "    ";

    public static void main(String[] args) {
        printDirectoryDeeply(ROOT_PATH, "");
    }

    private static void printDirectoryDeeply(String filePath, String indent) {
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        if (childFiles == null) {
            System.out.println(indent + "File: " + file.getName());
            return;
        }

        System.out.println(indent + "Directory: " + file.getName());
        for (File f: childFiles) {
            printDirectoryDeeply(f.getPath(), indent + INDENT);
        }
    }
}
