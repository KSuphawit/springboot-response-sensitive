package com.example.responsesensitive.utils;

import org.springframework.util.ClassUtils;

public class CommonUtils {

    public static boolean isPrimitiveOrString(Class<?> clazz) {
        return ClassUtils.isPrimitiveOrWrapper(clazz) || String.class == clazz;
    }
}
