package com.ff.backend.dto.request;

import com.ff.backend.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 角色表(Role)修改请求 DTO
 *
 * @author makejava
 * @since 2026-10-03 12:27:12
 */
@Data
@NoArgsConstructor
public class RoleUpdateRequest {

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
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除：0未删除，1已删除
     */
    private Integer isDelete;

    private List<Long> permissionIds;

    /**
     * 从实体复制对应字段；实体为 null 时，各字段保留 null。
     */
    public RoleUpdateRequest(Role entity) {
        if (entity == null) {
            return;
        }
        this.name = entity.getName();
        this.code = entity.getCode();
        this.description = entity.getDescription();
        this.updateTime = entity.getUpdateTime();
        this.isDelete = entity.getIsDelete();
        // Role 实体不包含权限列表，permissions 保留 null，由业务逻辑补充。
    }

}

