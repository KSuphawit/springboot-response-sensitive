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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            return new BaseResponse<>(true, "successfully");
        }

        if (!isSensitiveApi(returnType.getAnnotatedElement())) return body;

        if (body instanceof BaseResponse) {
            Object removed = removeSensitiveInformation(((BaseResponse<?>) body).getData());
            return new BaseResponse<>(((BaseResponse<?>) body).getSuccess(), removed);
        }

        return removeSensitiveInformation(body);
    }

    public <T> T removeSensitiveInformation(T body) {
        List<Field> fields = Arrays.asList(body.getClass().getDeclaredFields());
        List<String> nonSensitiveFields = fields.stream()
                .filter(field -> !field.isAnnotationPresent(Sensitive.class))
                .map(Field::getName)
                .collect(Collectors.toList());
        try {
            T output = (T) body.getClass().getDeclaredConstructor().newInstance();
            CommonUtils.copyPropsFrom(output, body, nonSensitiveFields);
            return output;
        } catch (Exception e) {
            logger.error("Failed to remove sensitive field ", e);
            return body;
        }
    }

    private boolean isSensitiveApi(AnnotatedElement annotatedElement) {
        return annotatedElement.getAnnotation(SensitiveAPI.class) != null;
    }
}
