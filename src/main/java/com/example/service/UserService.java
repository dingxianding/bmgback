package com.example.service;

import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * 描述：
 *
 * @author huchenqiang
 * @date 2018/8/22 18:32
 */
@Service
public class UserService {
    @Autowired
    private UserRepository repository;


    public static int getCurrentUserID() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public static int getCurrentUserRole() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
    }

}
