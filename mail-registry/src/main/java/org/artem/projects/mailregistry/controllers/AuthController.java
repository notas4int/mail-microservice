package org.artem.projects.mailregistry.controllers;


import lombok.RequiredArgsConstructor;
import org.artem.projects.mailregistry.dtos.RequestAuthDTO;
import org.artem.projects.mailregistry.dtos.RequestConfirmationCodeDTO;
import org.artem.projects.mailregistry.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/authenticate")
    public String auth(@RequestBody RequestAuthDTO requestAuthDTO) {
        return authService.auth(requestAuthDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody RequestAuthDTO requestRegisterDTO) {
        authService.register(requestRegisterDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/confirm-code")
    public String confirmCode(@RequestBody RequestConfirmationCodeDTO codeObj) {
        return authService.confirmCode(codeObj);
    }
}
