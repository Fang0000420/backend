package com.ff.backend.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 权限表(Permission)数据库实体类
 *
 * @author makejava
 * @since 2026-10-03 12:25:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 权限名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 权限编码，例如 activity:create
     */
    @TableField(value = "code")
    private String code;

    /**
     * 父权限ID，NULL表示顶级权限
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 权限类型：1菜单，2操作/API
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 逻辑删除：0未删除，1已删除
     */
    @TableLogic
    @TableField(value = "is_delete")
    private Integer isDelete;

}

