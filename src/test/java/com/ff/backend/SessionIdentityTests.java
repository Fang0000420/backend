package com.ff.backend;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ff.backend.config.SecurityConfig;
import com.ff.backend.controller.ActivityController;
import com.ff.backend.controller.AuthController;
import com.ff.backend.entity.Activity;
import com.ff.backend.entity.User;
import com.ff.backend.security.LoginUser;
import com.ff.backend.service.ActivityService;
import com.ff.backend.service.PermissionService;
import com.ff.backend.service.UserService;
import com.ff.backend.service.impl.DatabaseUserDetailsService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** 使用真实 Security 过滤器及登录流程；模拟数据服务，不访问数据库。 */
@SpringJUnitConfig(SessionIdentityTests.TestConfig.class)
@WebAppConfiguration
class SessionIdentityTests {

    private static final String ACTIVITY_JSON = """
            {"name":"Java 分享会","description":"测试活动","status":0,"capacity":10,
             "startTime":"2026-10-10T10:00:00Z","endTime":"2026-10-10T11:00:00Z"}
            """;

    @Configuration
    @EnableWebMvc
    @EnableWebSecurity
    @Import({SecurityConfig.class, AuthController.class, ActivityController.class,
            DatabaseUserDetailsService.class})
    static class TestConfig {
        @Bean UserService userService() { return mock(UserService.class); }
        @Bean PermissionService permissionService() { return mock(PermissionService.class); }
        @Bean ActivityService activityService() { return mock(ActivityService.class); }
    }

    @Autowired WebApplicationContext context;
    @Autowired UserService users;
    @Autowired PermissionService permissions;
    @Autowired ActivityService activities;
    private MockMvc mvc;

    @BeforeEach
    void prepare() {
        reset(users, permissions, activities);
        SecurityContextHolder.clearContext();
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        User user = User.builder().id(Long.MAX_VALUE).username("student").status(0)
                .passwordHash(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("test-only"))
                .build();
        when(users.findByUsername("student")).thenReturn(user);
        when(permissions.getPermissionByUserId(Long.MAX_VALUE)).thenReturn(
                List.of("activity:create", "activity:read", "activity:update", "activity:delete"));
        when(activities.save(any(Activity.class))).thenAnswer(invocation -> {
            invocation.<Activity>getArgument(0).setId(100L);
            return true;
        });
    }

    private Cookie csrfCookie(MockHttpSession session) throws Exception {
        var request = get("/login").accept(MediaType.TEXT_HTML);
        if (session != null) request.session(session);
        Cookie cookie = mvc.perform(request).andExpect(status().isOk()).andReturn()
                .getResponse().getCookie("XSRF-TOKEN");
        assertNotNull(cookie);
        return cookie;
    }

    private MockHttpSession signIn() throws Exception {
        Cookie csrf = csrfCookie(null);
        MvcResult result = mvc.perform(post("/login").cookie(csrf)
                        .header("X-XSRF-TOKEN", csrf.getValue())
                        .param("username", "student").param("password", "test-only"))
                .andExpect(status().is3xxRedirection()).andReturn();
        MockHttpSession session = (MockHttpSession) result.getRequest().getSession(false);
        assertNotNull(session);
        assertNotNull(session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY));
        return session;
    }

    @Test
    void loginKeepsDatabaseIdInSessionAndReturnsSafeDto() throws Exception {
        MockHttpSession session = signIn();
        SecurityContext security = (SecurityContext) session.getAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        LoginUser principal = assertInstanceOf(LoginUser.class, security.getAuthentication().getPrincipal());
        assertEquals(Long.MAX_VALUE, principal.getUserId());
        assertNull(principal.getPassword(), "认证成功后仍应清除密码哈希");

        mvc.perform(get("/auth/me").session(session)).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("9223372036854775807"))
                .andExpect(jsonPath("$.username").value("student"))
                .andExpect(jsonPath("$.authorities").isArray())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.passwordHash").doesNotExist());

        // 登录对象可序列化，Session 持久化时 ID 也不会丢失。
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream output = new ObjectOutputStream(bytes)) { output.writeObject(security); }
        try (ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            SecurityContext restored = (SecurityContext) input.readObject();
            assertEquals(Long.MAX_VALUE, ((LoginUser) restored.getAuthentication().getPrincipal()).getUserId());
        }
    }

    @Test
    void createDoesNotRequireClientUserId() throws Exception {
        MockHttpSession session = signIn();
        Cookie csrf = csrfCookie(session);
        mvc.perform(post("/api/activity").session(session).cookie(csrf)
                        .header("X-XSRF-TOKEN", csrf.getValue()).contentType(MediaType.APPLICATION_JSON)
                        .content(ACTIVITY_JSON)).andExpect(status().isCreated());
        ArgumentCaptor<Activity> saved = ArgumentCaptor.forClass(Activity.class);
        verify(activities).save(saved.capture());
        assertEquals(Long.MAX_VALUE, saved.getValue().getUserId());
        verify(activities).createActivity(Long.MAX_VALUE, 100L);
    }

    @Test
    void forgedUserIdDoesNotChangeCreator() throws Exception {
        MockHttpSession session = signIn();
        Cookie csrf = csrfCookie(session);
        mvc.perform(post("/api/activity").session(session).cookie(csrf)
                        .header("X-XSRF-TOKEN", csrf.getValue()).contentType(MediaType.APPLICATION_JSON)
                        .content(ACTIVITY_JSON.replace("\"name\":", "\"userId\":999,\"name\":")))
                .andExpect(status().isCreated());
        verify(activities).createActivity(Long.MAX_VALUE, 100L);
        verify(activities, never()).createActivity(eq(999L), anyLong());
    }

    @Test
    void listIgnoresForgedQueryIdentity() throws Exception {
        when(activities.listActivities(1, 10, Long.MAX_VALUE)).thenReturn(new Page<>(1, 10));
        mvc.perform(get("/api/activity").session(signIn()).param("userId", "999"))
                .andExpect(status().isOk());
        verify(activities).listActivities(1, 10, Long.MAX_VALUE);
    }

    @Test
    void deleteUsesSessionIdentityAndKeepsExistingPermissionCheck() throws Exception {
        MockHttpSession session = signIn();
        Cookie csrf = csrfCookie(session);
        when(activities.haveActivity(Long.MAX_VALUE, 100L)).thenReturn(true);
        when(activities.getById(100L)).thenReturn(Activity.builder().id(100L).build());
        mvc.perform(delete("/api/activity/100").session(session).cookie(csrf)
                        .header("X-XSRF-TOKEN", csrf.getValue()).param("userId", "999"))
                .andExpect(status().isNoContent());
        verify(activities).haveActivity(Long.MAX_VALUE, 100L);
        verify(activities).removeById(any(Activity.class));
    }

    @Test
    void deleteIsForbiddenWhenCurrentUserHasNoActivityRole() throws Exception {
        MockHttpSession session = signIn();
        Cookie csrf = csrfCookie(session);
        mvc.perform(delete("/api/activity/100").session(session).cookie(csrf)
                        .header("X-XSRF-TOKEN", csrf.getValue())).andExpect(status().isForbidden());
        verify(activities, never()).removeById(any(Activity.class));
    }

    @Test
    void updateCannotChangeOwner() throws Exception {
        MockHttpSession session = signIn();
        Cookie csrf = csrfCookie(session);
        Activity existing = Activity.builder().id(100L).userId(42L).build();
        when(activities.getById(100L)).thenReturn(existing);
        mvc.perform(put("/api/activity/100").session(session).cookie(csrf)
                        .header("X-XSRF-TOKEN", csrf.getValue()).contentType(MediaType.APPLICATION_JSON)
                        .content(ACTIVITY_JSON.replace("\"name\":", "\"userId\":999,\"name\":")))
                .andExpect(status().isOk());
        assertEquals(42L, existing.getUserId());
    }

    @Test
    void authenticatedWriteWithoutCsrfIsRejected() throws Exception {
        mvc.perform(post("/api/activity").session(signIn()).contentType(MediaType.APPLICATION_JSON)
                        .content(ACTIVITY_JSON)).andExpect(status().isForbidden());
        verify(activities, never()).save(any(Activity.class));
    }

    @Test
    void incorrectPasswordAndDisabledAccountCannotLogin() throws Exception {
        Cookie csrf = csrfCookie(null);
        mvc.perform(post("/login").cookie(csrf).header("X-XSRF-TOKEN", csrf.getValue())
                        .param("username", "student").param("password", "wrong"))
                .andExpect(redirectedUrl("/login?error"));
        when(users.findByUsername("student")).thenReturn(User.builder().id(1L).username("student")
                .status(1).passwordHash(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("test-only")).build());
        csrf = csrfCookie(null);
        mvc.perform(post("/login").cookie(csrf).header("X-XSRF-TOKEN", csrf.getValue())
                        .param("username", "student").param("password", "test-only"))
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    void logoutInvalidatesSession() throws Exception {
        MockHttpSession session = signIn();
        Cookie csrf = csrfCookie(session);
        mvc.perform(post("/logout").session(session).cookie(csrf).header("X-XSRF-TOKEN", csrf.getValue()))
                .andExpect(status().is3xxRedirection());
        assertTrue(session.isInvalid());
        mvc.perform(get("/auth/me").accept(MediaType.TEXT_HTML)).andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("/login")));
    }

    @Test
    void oldSessionWithoutDatabaseIdRequiresNewLogin() throws Exception {
        var oldPrincipal = org.springframework.security.core.userdetails.User.withUsername("student")
                .password("unused-test-hash").authorities("activity:create").build();
        var security = SecurityContextHolder.createEmptyContext();
        security.setAuthentication(org.springframework.security.authentication.UsernamePasswordAuthenticationToken
                .authenticated(oldPrincipal, null, oldPrincipal.getAuthorities()));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, security);
        mvc.perform(get("/auth/me").session(session)).andExpect(status().isUnauthorized());
    }
}
