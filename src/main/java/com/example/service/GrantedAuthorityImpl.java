package com.example.service;

import org.springframework.security.core.GrantedAuthority;

/**
 * 描述：权限类型，负责存储权限和角色
 *
 * @author huchenqiang
 * @date 2018/8/20 16:58
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
