//package com.instagram.common.rateLimiter;
//
//import io.github.resilience4j.ratelimiter.RateLimiter;
//import io.github.resilience4j.ratelimiter.RateLimiterConfig;
//import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//import java.time.Duration;
//
//@Configuration
//public class RateLimiterCfg {
//    @Bean
//    public RateLimiterRegistry rateLimiterRegistry() {
//        RateLimiterConfig config = RateLimiterConfig.custom()
//                .timeoutDuration(Duration.ofSeconds(5))
//                .limitRefreshPeriod(Duration.ofSeconds(30))
//                .limitForPeriod(1)
//                .build();
//
//        return RateLimiterRegistry.of(config);
//    }
//
//    /**
//     *      RateLimiterConfig config = RateLimiterConfig.custom()
//     *                 .timeoutDuration(Duration.ofSeconds(5))
//     *                 .limitRefreshPeriod(Duration.ofSeconds(10))
//     *                 .limitForPeriod(5)
//     * The rate limiter will allow up to 5 requests every 10 seconds (limitForPeriod(5) and limitRefreshPeriod(Duration.ofSeconds(10))).
//     * If a request is unable to acquire a permit within 5 seconds, it will timeout (timeoutDuration(Duration.ofSeconds(5))).
//     * @param rateLimiterRegistry
//     * @return
//     */
//
//    @Bean
//    public RateLimiter rateLimiter(RateLimiterRegistry rateLimiterRegistry) {
//        return rateLimiterRegistry.rateLimiter("backendA");
//    }
//}
