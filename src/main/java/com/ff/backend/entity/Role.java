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
 * 角色表(Role)数据库实体类
 *
 * @author makejava
 * @since 2026-10-03 12:27:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 角色名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 角色编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 角色说明
     */
    @TableField(value = "description")
    private String description;

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

