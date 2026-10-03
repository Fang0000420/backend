package com.ff.backend.controller;


import com.ff.backend.dto.request.RoleCreateRequest;
import com.ff.backend.dto.request.RoleUpdateRequest;
import com.ff.backend.dto.response.RoleResponse;
import com.ff.backend.entity.Role;
import com.ff.backend.service.RoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


/**
 * 角色表(Role)REST 控制器
 *
 * @author makejava
 * @since 2026-10-03 17:27:04
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    /**
     * 分页查询数据
     */
    @GetMapping
    @PreAuthorize("hasAuthority('role:read')")
    public Page<RoleResponse> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Role> entityPage = this.roleService.listPage(page, size);
        Page<RoleResponse> response = new Page<>(
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
    @PreAuthorize("hasAuthority('role:read')")
    public RoleResponse getById(
            @PathVariable Long id) {
        return toResponse(getEntityOrThrow(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    @PreAuthorize("hasAuthority('role:create')")
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponse create(
            @RequestBody RoleCreateRequest request) {

        Role entity = new Role();
        BeanUtils.copyProperties(request, entity);

        this.roleService.save(entity);
        return toResponse(entity);
    }

    /**
     * 修改数据
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('role:update')")
    public RoleResponse update(
            @PathVariable Long id,
            @RequestBody RoleUpdateRequest request) {

        Role entity = getEntityOrThrow(id);
        BeanUtils.copyProperties(request, entity);

        this.roleService.updateById(entity);
        return toResponse(entity);
    }

    /**
     * 根据主键删除数据
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('role:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        Role entity = getEntityOrThrow(id);
        this.roleService.removeById(entity);
    }

    /**
     * 根据主键获取实体，不存在时返回 404
     */
    private Role getEntityOrThrow(
            Long id) {

        Role entity = this.roleService.getById(id);

        if (entity == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Role not found: " + id
            );
        }

        return entity;
    }

    /**
     * Entity -> Response
     */
    private RoleResponse toResponse(
            Role entity) {

        RoleResponse response =
                new RoleResponse();

        BeanUtils.copyProperties(entity, response);
        return response;
    }
}

