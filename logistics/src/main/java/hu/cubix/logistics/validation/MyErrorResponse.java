package hu.cubix.logistics.validation;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MyErrorResponse {

    private String message;
    private List<String> validationErrors = new ArrayList<>();

    public MyErrorResponse(String message) {
        this.message = message;
    }
}
