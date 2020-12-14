package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;

public class MainReflection {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

       Resume r = (Resume) Resume.class.getConstructor().newInstance();
        System.out.println(r.getClass().getMethod("toString").invoke(r));
    }
}
