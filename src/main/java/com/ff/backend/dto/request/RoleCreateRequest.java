package com.ff.backend.dto.request;

import java.util.Date;

import lombok.Data;

/**
 * 角色表(Role)新增请求 DTO
 *
 * @author makejava
 * @since 2026-10-03 12:27:12
 */
@Data
public class RoleCreateRequest {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色说明
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除：0未删除，1已删除
     */
    private Integer isDelete;

}

