package com.example.responsesensitive.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {

    public static <T, R> void copyPropsFrom(T output, R fromObject, List<String> specificFields) throws IllegalAccessException {
        Field[] outputProps = output.getClass().getDeclaredFields();
        List<Field> sourceProps = Arrays.asList(fromObject.getClass().getDeclaredFields());

        if (!specificFields.isEmpty()) {
            sourceProps = sourceProps.stream().filter(field -> specificFields.contains(field.getName())).collect(Collectors.toList());
        }

        for (Field targetField : outputProps) {
            Field matchedField = sourceProps.stream()
                    .filter(field -> field.getName().equals(targetField.getName()) && field.getType() == targetField.getType()).findFirst()
                    .orElse(null);

            if (matchedField == null) continue;

            matchedField.setAccessible(true);
            targetField.setAccessible(true);
            targetField.set(output, matchedField.get(fromObject));
        }
    }
}
