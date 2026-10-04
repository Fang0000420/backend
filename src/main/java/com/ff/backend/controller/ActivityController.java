package com.ff.backend.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ff.backend.dto.request.ActivityCreateRequest;
import com.ff.backend.dto.request.ActivityUpdateRequest;
import com.ff.backend.dto.response.ActivityResponse;
import com.ff.backend.entity.Activity;
import com.ff.backend.security.LoginUser;
import com.ff.backend.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


/**
 * 活动信息表(Activity)REST 控制器
 *
 * @author makejava
 * @since 2026-10-01 11:12:37
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activity")
public class ActivityController {

    private final ActivityService activityService;

    /**
     * 查询全部数据
     */
    @GetMapping
    public Page<ActivityResponse> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @AuthenticationPrincipal LoginUser currentUser) {
        Page<Activity> activityPage = this.activityService.listActivities(page, size, currentUser.getUserId());
        Page<ActivityResponse> response = new Page<>(activityPage.getCurrent(), activityPage.getSize(), activityPage.getTotal());

        response.setRecords(activityPage.getRecords().stream().map(this::toResponse).toList());
        return response;
    }

    /**
     * 根据主键查询单条数据
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('activity:read')")
    public ActivityResponse getById(@PathVariable Long id) {
        return toResponse(getEntityOrThrow(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('activity:create')")
    public ActivityResponse create(@Valid @RequestBody ActivityCreateRequest request, @AuthenticationPrincipal LoginUser currentUser) {

        Activity entity = new Activity();
        BeanUtils.copyProperties(request, entity);
        entity.setUserId(currentUser.getUserId());

        this.activityService.save(entity);
        activityService.createActivity(entity.getUserId(), entity.getId());
        return toResponse(entity);
    }

    /**
     * 修改数据
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('activity:update')")
    public ActivityResponse update(@PathVariable Long id, @Valid @RequestBody ActivityUpdateRequest request, @AuthenticationPrincipal LoginUser currentUser) {
        Long userId = currentUser.getUserId();
        boolean b = this.activityService.haveActivity(userId, id);
        if (!b) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "noAuthority");
        }
        Activity entity = getEntityOrThrow(id);
        BeanUtils.copyProperties(request, entity);

        this.activityService.updateById(entity);
        return toResponse(entity);
    }

    /**
     * 根据主键删除数据
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('activity:delete')")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal LoginUser currentUser) {
        boolean b = this.activityService.haveActivity(currentUser.getUserId(), id);
        if (!b) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "noAuthority");
        }
        Activity entity = getEntityOrThrow(id);
        this.activityService.removeById(entity);
    }

    /**
     * 根据主键获取实体，不存在时返回 404
     */
    private Activity getEntityOrThrow(Long id) {

        Activity entity = this.activityService.getById(id);

        if (entity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found: " + id);
        }

        return entity;
    }

    /**
     * Entity -> Response
     */
    private ActivityResponse toResponse(Activity entity) {

        ActivityResponse response = new ActivityResponse();

        BeanUtils.copyProperties(entity, response);
        return response;
    }
}

