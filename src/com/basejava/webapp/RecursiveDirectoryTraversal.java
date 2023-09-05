package com.basejava.webapp;

import java.io.File;

public class RecursiveDirectoryTraversal {

    private static final String ROOT_PATH = "C:\\Users\\dimac\\Desktop\\basejava";

    public static void main(String[] args) {
        printFile(ROOT_PATH);
    }

    private static void printFile(String filePath) {
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        if (childFiles == null) {
            System.out.println(file.getName());
            return;
        }

        for (File f: childFiles) {
            printFile(f.getPath());
        }
    }
}
