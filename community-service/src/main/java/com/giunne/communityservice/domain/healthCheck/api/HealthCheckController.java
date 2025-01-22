package com.giunne.communityservice.domain.healthCheck.api;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
@Slf4j
public class HealthCheckController {

    @GetMapping("/health-check")
    public String check(HttpServletRequest request) {
        log.info("Server port={}", request.getServerPort());
        return "community-service: health-check";
    }

}
