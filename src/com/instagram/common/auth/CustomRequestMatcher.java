//package com.instagram.common.auth;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomRequestMatcher implements RequestMatcher {
//
//    @Override
//    public boolean matches(HttpServletRequest request) {
//        // Custom logic to match non-existent endpoints (this could be based on controller paths, etc.)
//        // If no matching endpoint, return true to trigger the 403
//        return true;
//    }
//
//}
