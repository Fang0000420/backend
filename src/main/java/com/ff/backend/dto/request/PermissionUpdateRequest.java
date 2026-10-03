package com.ff.backend.dto.request;

import java.util.Date;

import lombok.Data;

/**
 * 权限表(Permission)修改请求 DTO
 *
 * @author makejava
 * @since 2026-10-03 12:25:51
 */
@Data
public class PermissionUpdateRequest {

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限编码，例如 activity:create
     */
    private String code;

    /**
     * 父权限ID，NULL表示顶级权限
     */
    private Long parentId;

    /**
     * 权限类型：1菜单，2操作/API
     */
    private Integer type;


    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除：0未删除，1已删除
     */
    private Integer isDelete;

}

