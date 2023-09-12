package com.basejava.webapp;

import java.io.File;

public class RecursiveDirectoryTraversal {

    private static final String ROOT_PATH = "C:\\Users\\dimac\\Desktop\\basejava";

    public static void main(String[] args) {
        printDirectoryDeeply(ROOT_PATH);
    }

    // TODO: make pretty output
    private static void printDirectoryDeeply(String filePath) {
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        if (childFiles == null) {
            System.out.println("File: " + file.getName());
            return;
        }

        System.out.println("Directory: " + file.getName());
        for (File f: childFiles) {
            printDirectoryDeeply(f.getPath());
        }
    }
}
