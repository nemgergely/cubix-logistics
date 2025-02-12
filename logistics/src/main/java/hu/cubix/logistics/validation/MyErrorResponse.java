package hu.cubix.logistics.validation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Setter
public class MyErrorResponse {

    private String message;
    private List<String> errors;

    public MyErrorResponse(String message) {
        this.message = message;
    }
}
