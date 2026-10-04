package com.ff.backend.dto.request;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.ff.backend.entity.User;

/**
 * 用户表(User)新增请求 DTO
 *
 * @author makejava
 * @since 2026-10-03 12:28:15
 */
@Data
@NoArgsConstructor
public class UserCreateRequest {

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 创建账号时提交的明文密码，由服务端编码后保存
     */
    private String password;

    /**
     * 用户姓名/显示名称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 账号状态：0表示可用
     */
    private Integer status;

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

    /** 从实体复制对应字段；实体为 null 时，各字段保留 null。 */
    public UserCreateRequest(User entity) {
        if (entity == null) {
            return;
        }
        this.username = entity.getUsername();
        this.name = entity.getName();
        this.phone = entity.getPhone();
        this.email = entity.getEmail();
        this.address = entity.getAddress();
        this.status = entity.getStatus();
        this.createTime = entity.getCreateTime();
        this.updateTime = entity.getUpdateTime();
        this.isDelete = entity.getIsDelete();
        // password 接收明文，不能从 passwordHash 复制；保留 null，由调用方填入。
    }

}

