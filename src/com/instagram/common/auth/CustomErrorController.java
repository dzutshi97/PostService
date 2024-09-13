//package com.instagram.common.auth;
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.server.ResponseStatusException;
//
//@Controller
//public class CustomErrorController implements ErrorController {
//
//    @RequestMapping("/error")
//    public String handleError() {
//        // Return a 403 Forbidden response
//        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
//    }
//}
