package com.ff.backend.controller;

import com.ff.backend.dto.response.CurrentUserResponse;
import com.ff.backend.security.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthController {

    @GetMapping("/auth/me")
    public CurrentUserResponse me(@AuthenticationPrincipal LoginUser currentUser) {
        // 发布新认证对象后，旧 Session 中仍可能保存不含 ID 的框架 User。
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "请重新登录");
        }
        return new CurrentUserResponse(
                currentUser.getUserId().toString(),
                currentUser.getUsername(),
                currentUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );
    }
}
