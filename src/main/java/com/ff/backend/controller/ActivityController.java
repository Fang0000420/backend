package com.ff.backend.controller;


import com.ff.backend.dto.request.ActivityCreateRequest;
import com.ff.backend.dto.request.ActivityUpdateRequest;
import com.ff.backend.dto.response.ActivityResponse;
import com.ff.backend.entity.Activity;
import com.ff.backend.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * 活动信息表(Activity)REST 控制器
 *
 * @author makejava
 * @since 2026-09-29 14:04:49
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
    public List<ActivityResponse> list() {
        return this.activityService.list()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * 根据主键查询单条数据
     */
    @GetMapping("/{id}")
    public ActivityResponse getById(
            @PathVariable Long id) {
        return toResponse(getEntityOrThrow(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityResponse create(
            @RequestBody ActivityCreateRequest request) {

        Activity entity = new Activity();
        BeanUtils.copyProperties(request, entity);

        this.activityService.save(entity);
        return toResponse(entity);
    }

    /**
     * 修改数据
     */
    @PutMapping("/{id}")
    public ActivityResponse update(
            @PathVariable Long id,
            @RequestBody ActivityUpdateRequest request) {

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
    public void delete(
            @PathVariable Long id) {

        Activity entity = getEntityOrThrow(id);
        this.activityService.removeById(entity);
    }

    /**
     * 根据主键获取实体，不存在时返回 404
     */
    private Activity getEntityOrThrow(
            Long id) {

        Activity entity = this.activityService.getById(id);

        if (entity == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Activity not found: " + id
            );
        }

        return entity;
    }

    /**
     * Entity -> Response
     */
    private ActivityResponse toResponse(
            Activity entity) {

        ActivityResponse response =
                new ActivityResponse();

        BeanUtils.copyProperties(entity, response);
        return response;
    }
}

