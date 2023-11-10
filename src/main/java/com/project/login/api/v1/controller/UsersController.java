package com.project.login.api.v1.controller;

import com.project.login.api.jwt.JwtTokenProvider;
import com.project.login.api.lib.Helper;
import com.project.login.api.security.CustomUserDetails;
import com.project.login.api.security.annotation.AuthUser;
import com.project.login.api.v1.dto.Response;
import com.project.login.api.v1.dto.request.UserRequestDto;
import com.project.login.api.v1.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UsersController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;
    private final Response response;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Validated UserRequestDto.SignUp signUp, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.signUp(signUp);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated UserRequestDto.Login login, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.login(login);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo1(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info(customUserDetails.getUsername());
        return response.success();
    }

    @GetMapping("/custom")
    public ResponseEntity<?> getInfo2(@AuthUser CustomUserDetails customUserDetails) {
        log.info(customUserDetails.getUsername());
        return response.success();
    }
}
