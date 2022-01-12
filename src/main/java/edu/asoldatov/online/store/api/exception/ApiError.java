package edu.asoldatov.online.store.api.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
public class ApiError {
    private HttpStatus status;
    private List<Error> errors;
}
