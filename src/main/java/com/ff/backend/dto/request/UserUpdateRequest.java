package com.ff.backend.dto.request;

import java.util.Date;

import lombok.Data;

/**
 * 用户表(User)修改请求 DTO
 *
 * @author makejava
 * @since 2026-10-03 12:28:15
 */
@Data
public class UserUpdateRequest {

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 旧密码
     */
    private String passWordOld;


    /**
     * 新密码
     */
    private String passWordNew;

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
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除：0未删除，1已删除
     */
    private Integer isDelete;

}

