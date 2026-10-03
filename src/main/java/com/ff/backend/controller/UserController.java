package com.ff.backend.controller;


import com.ff.backend.dto.request.UserCreateRequest;
import com.ff.backend.dto.request.UserUpdateRequest;
import com.ff.backend.dto.response.UserResponse;
import com.ff.backend.entity.User;
import com.ff.backend.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


/**
 * 用户表(User)REST 控制器
 *
 * @author makejava
 * @since 2026-10-03 17:27:11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 分页查询数据
     */
    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public Page<UserResponse> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<User> entityPage = this.userService.listPage(page, size);
        Page<UserResponse> response = new Page<>(
                entityPage.getCurrent(),
                entityPage.getSize(),
                entityPage.getTotal()
        );

        response.setRecords(
                entityPage.getRecords().stream()
                        .map(this::toResponse)
                        .toList()
        );
        return response;
    }

    /**
     * 根据主键查询单条数据
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public UserResponse getById(
            @PathVariable Long id) {
        return toResponse(getEntityOrThrow(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(
            @RequestBody UserCreateRequest request) {

        User entity = new User();
        BeanUtils.copyProperties(request, entity);
        entity.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        this.userService.save(entity);
        return toResponse(entity);
    }

    /**
     * 修改数据
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public UserResponse update(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request) {

        User entity = getEntityOrThrow(id);
        BeanUtils.copyProperties(request, entity);

        this.userService.updateById(entity);
        return toResponse(entity);
    }

    /**
     * 根据主键删除数据
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        User entity = getEntityOrThrow(id);
        this.userService.removeById(entity);
    }

    /**
     * 根据主键获取实体，不存在时返回 404
     */
    private User getEntityOrThrow(
            Long id) {

        User entity = this.userService.getById(id);

        if (entity == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found: " + id
            );
        }

        return entity;
    }

    /**
     * Entity -> Response
     */
    private UserResponse toResponse(
            User entity) {

        UserResponse response =
                new UserResponse();

        BeanUtils.copyProperties(entity, response);
        return response;
    }
}

