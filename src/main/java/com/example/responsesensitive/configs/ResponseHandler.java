package com.example.responsesensitive.configs;


import com.example.responsesensitive.annotations.Sensitive;
import com.example.responsesensitive.annotations.SensitiveAPI;
import com.example.responsesensitive.models.BaseResponse;
import com.example.responsesensitive.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.responsesensitive.utils.CommonUtils.isPrimitiveOrString;

@ControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {

    private final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (Objects.requireNonNull(returnType.getMethod()).getReturnType() == Void.TYPE) {
            return new BaseResponse<>(true, null);
        }

        // if sensitive controller always remove sensitive information
        if (isSensitiveController(returnType)) {
            return removeSensitive(body);
        }

        // if not sensitive api can return data with sensitive information
        if (!isSensitiveApi(returnType.getAnnotatedElement())) return body;

        return removeSensitive(body);
    }

    private Object removeSensitive(Object body) {
        Object data = body instanceof BaseResponse ? ((BaseResponse<?>) body).getData() : body;
        Object removed = data instanceof List ? removeListSensitiveInformation((List<?>) data) : removeSensitiveInformation(data);
        return body instanceof BaseResponse ? new BaseResponse<>(((BaseResponse<?>) body).getSuccess(), removed) : removed;
    }

    private <T> T removeSensitiveInformation(T data) {
        // if data == null or data type sensitive return null
        if (data == null || data.getClass().isAnnotationPresent(Sensitive.class)) return null;

        if (isPrimitiveOrString(data.getClass())) return data;

        List<Field> fields = Arrays.asList(data.getClass().getDeclaredFields());
        List<Field> sensitiveFields = fields.stream()
                .filter(field -> field.isAnnotationPresent(Sensitive.class))
                .collect(Collectors.toList());
        List<Field> nonsensitiveFields = new ArrayList<>(fields);
        nonsensitiveFields.removeAll(sensitiveFields);

        // if nonsensitiveFields is empty mean that object no any field to return
        if (nonsensitiveFields.isEmpty()) return null;

        try {
            // set sensitive to null
            for (Field sensitiveField : sensitiveFields) {
                sensitiveField.setAccessible(true);
                sensitiveField.set(data, null);
            }
            // check nonsensitive object if it has sensitive field set to null
            for (Field nonsensitiveField : nonsensitiveFields) {
                nonsensitiveField.setAccessible(true);
                Object fieldValue = nonsensitiveField.get(data);
                Object removed = fieldValue instanceof List ? removeListSensitiveInformation((List<?>) fieldValue) : removeSensitiveInformation(fieldValue);
                nonsensitiveField.set(data, removed);
            }
        } catch (Exception e) {
            logger.error("Failed to remove sensitive information ", e);
        }

        return data;
    }

    private <T> List<T> removeListSensitiveInformation(List<T> listData) {
        if (CollectionUtils.isEmpty(listData)) return listData;
        return listData.stream().map(this::removeSensitiveInformation).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private boolean isSensitiveController(MethodParameter returnType) {
        return returnType.getDeclaringClass().isAnnotationPresent(SensitiveAPI.class);
    }

    private boolean isSensitiveApi(AnnotatedElement annotatedElement) {
        return annotatedElement.getAnnotation(SensitiveAPI.class) != null;
    }
}
