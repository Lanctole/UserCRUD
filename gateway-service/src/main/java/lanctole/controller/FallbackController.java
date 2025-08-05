package lanctole.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/fallback/users")
    public ResponseEntity<String> fallbackUsers() {
        return ResponseEntity.ok("User service is temporarily unavailable. Please try later.");
    }
}
