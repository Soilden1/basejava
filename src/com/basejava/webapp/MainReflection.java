package com.basejava.webapp;

import com.basejava.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("Name");
        Class<? extends Resume> resumeClass = resume.getClass();
        Field field = resumeClass.getDeclaredFields()[0];
        System.out.println(field.getName());
        field.setAccessible(true);
        System.out.println(field.get(resume));
        field.set(resume, "new_uuid");

        Method method = resumeClass.getMethod("toString");
        Object result = method.invoke(resume);
        System.out.println(result);
    }
}
