package com.ff.backend.dto.response;

import com.ff.backend.entity.User;

import java.util.List;

/** 只返回前端需要的身份信息，不暴露密码或完整认证对象。 */
public record CurrentUserResponse(String id, String username, List<String> authorities) {
    /** User 不包含权限列表，authorities 保留 null，由业务逻辑补充。 */
    public CurrentUserResponse(User entity) {
        this(entity == null || entity.getId() == null ? null : entity.getId().toString(),
                entity == null ? null : entity.getUsername(), null);
    }
}
