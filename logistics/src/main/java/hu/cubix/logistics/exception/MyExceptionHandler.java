package hu.cubix.logistics.exception;

import hu.cubix.logistics.controller.AddressController;
import hu.cubix.logistics.controller.TransportPlanController;
import hu.cubix.logistics.validation.MyErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice(basePackageClasses = {AddressController.class, TransportPlanController.class})
public class MyExceptionHandler {

    @ExceptionHandler({IdMismatchException.class, MilestoneWithoutSectionException.class})
    public ResponseEntity<MyErrorResponse> handleIdAndMilestoneMismatch(RuntimeException e, WebRequest request) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new MyErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<MyErrorResponse> handleDataNotFound(RecordNotFoundException e, WebRequest request) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new MyErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MyErrorResponse> handleMethodArgumentNotValid(
        MethodArgumentNotValidException e, WebRequest request) {

        List<String> errors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();

        MyErrorResponse myErrorResponse = new MyErrorResponse("One or more fields have failed the validation");
        myErrorResponse.setValidationErrors(errors);

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(myErrorResponse);
    }
}
