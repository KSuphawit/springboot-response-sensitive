package com.example.responsesensitive.utils;

import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {

    public static boolean isPrimitiveOrString(Class<?> clazz) {
        return ClassUtils.isPrimitiveOrWrapper(clazz) || String.class == clazz;
    }
}
