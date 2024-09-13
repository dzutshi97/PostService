package com.instagram.common.exception;

import com.instagram.common.exception.custom.PlayerNotFoundException;
import com.instagram.common.lang.Result;
//import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
//import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handle(Exception ex,
//                                         HttpServletRequest request, HttpServletResponse response) {
//        if (ex instanceof PlayerNotFoundException) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = PlayerNotFoundException.class)
    public Result handler(PlayerNotFoundException e) {
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) {

        BindingResult result = e.getBindingResult();
        ObjectError objectError = result.getAllErrors().stream().findFirst().get();
        return Result.fail(objectError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) {
        return Result.fail(e.getMessage());
    }

//    @ExceptionHandler({RequestNotPermitted.class })
//    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
//    public Result requestNotPermitted(RequestNotPermitted e) {
//        return Result.fail("Max retries exceeded error: " + e.getMessage());
//    }
//
//    @ExceptionHandler({CallNotPermittedException.class})
//    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
//    public Result handleCallNotPermittedException(CallNotPermittedException e) {
//        return Result.fail("Circuit breaker open: " + e.getMessage());
//    }
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleForbiddenAccessException(NoResourceFoundException e) {
        Result r = new Result();
        r.setCode(403);
        r.setMsg("Forbidden request:" + e.getMessage());
        return r;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class) // or a more specific base exception class
    public Result handleGenericException(Exception e) {
        Result r = new Result();
        r.setCode(500);
        r.setMsg("An unexpected internal error occurred: " + e.getMessage());
        return r;
    }

}
