package 代码积累库.springmvc;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception exception) {
        return ResponseEntity.ok(exception.getMessage());
    }

    /**
     * jsr 303 校验 异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler//requestBody参数异常
    public ResponseEntity<String> arg(MethodArgumentNotValidException exception) {
        List<String> message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)//哪些字段错误了的信息
                .toList();
         return ResponseEntity.ok(String.join(",", message));
    }

    @ExceptionHandler//param
    public ResponseEntity<String> param(ConstraintViolationException exception) {
        exception.getConstraintViolations().stream();
        return ResponseEntity.ok("");
    }
}
