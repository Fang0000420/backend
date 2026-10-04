package com.ff.backend.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.Collection;
import java.util.Objects;

/** 登录身份，随 SecurityContext 保存在 Session 中。 */
@Getter
public class LoginUser extends User {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Long userId;

    public LoginUser(Long userId, String username, String passwordHash, boolean enabled,
                     Collection<? extends GrantedAuthority> authorities) {
        super(username, passwordHash, enabled, true, true, true, authorities);
        this.userId = Objects.requireNonNull(userId, "userId cannot be null");
    }

}
