package com.ff.backend.service.impl;

import com.ff.backend.entity.User;
import com.ff.backend.service.PermissionService;
import com.ff.backend.service.UserService;
import com.ff.backend.security.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final PermissionService permissionService;

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) {

        User user = userService.findByUsername(username);

        if (user == null) {
            log.debug("登录失败：未找到有效用户");
            throw new UsernameNotFoundException("用户不存在");
        }

        boolean enabled = Integer.valueOf(0).equals(user.getStatus());
        List<String> permission = permissionService.getPermissionByUserId(user.getId());
        String passwordHash = user.getPasswordHash();

        UserDetails details = new LoginUser(
                user.getId(), user.getUsername(), passwordHash, enabled,
                permission.stream().map(SimpleGrantedAuthority::new).toList()
        );
        log.debug("用户"+username+"登录");
        return details;
    }
}
