package com.nequi.franchises.manage_franchises.application.util;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.UUID;

public class LogUtil {
    public static Marker getMarker() {
        UUID uuid;
        uuid = UUID.randomUUID();
        return MarkerFactory.getMarker(uuid.toString());
    }

    public static String getExceptionAndSuperclass(Throwable t) {
        Class<?> currentClass = t.getClass();
        Class<?> superclass = currentClass.getSuperclass();
        return (superclass != null && superclass != Object.class ? superclass.getSimpleName() + "$" : "") + currentClass.getSimpleName();
    }
}
