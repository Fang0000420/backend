package com.ff.backend.controller;


import com.ff.backend.dto.request.PermissionCreateRequest;
import com.ff.backend.dto.request.PermissionUpdateRequest;
import com.ff.backend.dto.response.PermissionResponse;
import com.ff.backend.entity.Permission;
import com.ff.backend.service.PermissionService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


/**
 * 权限表(Permission)REST 控制器
 *
 * @author makejava
 * @since 2026-10-03 17:26:32
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/permission")
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * 分页查询数据
     */
    @GetMapping
    @PreAuthorize("hasAuthority('permission:read')")
    public Page<PermissionResponse> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Permission> entityPage = this.permissionService.listPage(page, size);
        Page<PermissionResponse> response = new Page<>(
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
    @PreAuthorize("hasAuthority('permission:read')")
    public PermissionResponse getById(
            @PathVariable Long id) {
        return toResponse(getEntityOrThrow(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    @PreAuthorize("hasAuthority('permission:create')")
    @ResponseStatus(HttpStatus.CREATED)
    public PermissionResponse create(
            @RequestBody PermissionCreateRequest request) {

        Permission entity = new Permission();
        BeanUtils.copyProperties(request, entity);

        this.permissionService.save(entity);
        return toResponse(entity);
    }

    /**
     * 修改数据
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('permission:update')")
    public PermissionResponse update(
            @PathVariable Long id,
            @RequestBody PermissionUpdateRequest request) {

        Permission entity = getEntityOrThrow(id);
        BeanUtils.copyProperties(request, entity);

        this.permissionService.updateById(entity);
        return toResponse(entity);
    }

    /**
     * 根据主键删除数据
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('permission:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        Permission entity = getEntityOrThrow(id);
        this.permissionService.removeById(entity);
    }

    /**
     * 根据主键获取实体，不存在时返回 404
     */
    private Permission getEntityOrThrow(
            Long id) {

        Permission entity = this.permissionService.getById(id);

        if (entity == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Permission not found: " + id
            );
        }

        return entity;
    }

    /**
     * Entity -> Response
     */
    private PermissionResponse toResponse(
            Permission entity) {

        PermissionResponse response =
                new PermissionResponse();

        BeanUtils.copyProperties(entity, response);
        return response;
    }
}

