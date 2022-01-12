package edu.asoldatov.online.store.api.exception;

import edu.asoldatov.online.store.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    private static final String DEFAULT_MESSAGE = "Уважаемый пользователь! Произошла непредвиденная ошибка.";
    private static final String DEFAULT_VALIDATION_MESSAGE = "Требуемое значение отсутствует или указано неверно.";

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleAll(final Exception ex) {
        return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "exception.internal.server", ex);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ApiError> handleNotFoundException(final NotFoundException ex) {
        return getResponseEntity(HttpStatus.NOT_FOUND, "exception.not.found", ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, Set<String>> errorsMap = fieldErrors.stream().collect(
                Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(error -> getMessage(error.getDefaultMessage(), DEFAULT_VALIDATION_MESSAGE),
                                Collectors.toSet())
                )
        );
        return new ResponseEntity<>(errorsMap.isEmpty() ? ex : errorsMap, headers, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiError> getResponseEntity(final HttpStatus httpStatus,
                                                       final String codeMessage,
                                                       final Throwable ex) {
        Throwable exception = ex;
        while (exception != null && StringUtils.isNotBlank(exception.getLocalizedMessage())) {
            exception = exception.getCause();
        }
        log.error("Exception occurred: " + ex.getMessage(), ex);

        ApiError apiError = new ApiError(httpStatus,
                Collections.singletonList(
                        Error.builder()
                                .errorCode(httpStatus.value())
                                .message(getMessage(codeMessage, DEFAULT_MESSAGE))
                                .detail(ex.getLocalizedMessage())
                                .build()
                ));

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    private String getMessage(final String codeMessage, final String defaultMessage) {
        return Optional.ofNullable(messageSource.getMessage(
                codeMessage,
                null,
                null,
                Locale.getDefault())).orElse(defaultMessage);
    }
}
