package lanctole.controller;

import lanctole.dto.EmailRequestDto;
import lanctole.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailRequestDto request) {
        emailService.sendEmail(request.getEmail(), request.getSubject(), request.getMessage());
        return ResponseEntity.ok().build();
    }
}
