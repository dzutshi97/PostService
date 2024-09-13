//package com.instagram.common.auth;
//
//import com.instagram.common.exception.custom.ForbiddenAccessException;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
//
//@Configuration
//public class SecurityConfig {
//
////    private final CustomAccessDeniedHandler customAccessDeniedHandler;
////
////    public SecurityConfig(CustomAccessDeniedHandler customAccessDeniedHandler) {
////        this.customAccessDeniedHandler = customAccessDeniedHandler;
////    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .anyRequest().permitAll()  // Allow all requests, including valid endpoints
//                )
//                .exceptionHandling(exceptionHandling ->
//                        exceptionHandling
//                                .accessDeniedHandler(customAccessDeniedHandler())  // Handle access denied
////                                .accessDeniedHandler((request, response, accessDeniedException) -> {
////                                    throw new ForbiddenAccessException("Access Denied: " + accessDeniedException.getMessage());
////                                })
//                )
//                .csrf(csrf -> csrf.disable());  // Disable CSRF protection if not needed
//
//        return http.build();
//    }
//
//
//    @Bean
//    public AccessDeniedHandler customAccessDeniedHandler() {
//        return (request, response, accessDeniedException) -> {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // Return 403 status code
//            response.getWriter().write("Access Denied");  // Custom message (optional)
//        };
//    }
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http, AccessDeniedHandler customAccessDeniedHandler) throws Exception {
////        return http
////                .authorizeRequests(authorizeRequests ->
////                        authorizeRequests
////                                .requestMatchers("/posts/**").authenticated()  // Your secured API endpoints
////                                .anyRequest().denyAll()  // Deny all unknown endpoints
////                )
////                .exceptionHandling(exceptionHandling ->
////                        exceptionHandling
////                                .accessDeniedHandler(customAccessDeniedHandler)  // Handle unknown endpoints with 403
////                )
////                .csrf(AbstractHttpConfigurer::disable  // Disable CSRF protection (if necessary)
////                )
////         .build();
////    }
//
//
//}
