package com.ff.backend.dto.response;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.ff.backend.entity.Role;

/**
 * 角色表(Role)响应 DTO
 *
 * @author makejava
 * @since 2026-10-03 12:27:12
 */
@Data
@NoArgsConstructor
public class RoleResponse {

    /**
     * 角色ID
     */
    private Long id;

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

    private List<Long> permissionIds;

    private List<PermissionResponse> permissions;

    /** 从实体复制对应字段；实体为 null 时，各字段保留 null。 */
    public RoleResponse(Role entity) {
        if (entity == null) {
            return;
        }
        this.id = entity.getId();
        this.name = entity.getName();
        this.code = entity.getCode();
        this.description = entity.getDescription();
        this.createTime = entity.getCreateTime();
        this.updateTime = entity.getUpdateTime();
        this.isDelete = entity.getIsDelete();
        // Role 实体不包含权限列表，permissions 保留 null，由业务逻辑补充。
    }

}

