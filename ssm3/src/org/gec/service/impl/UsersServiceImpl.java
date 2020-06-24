package org.gec.service.impl;

import org.gec.mapper.UsersMapper;
import org.gec.pojo.Users;
import org.gec.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users Login(String username, String password) {
        Users user = usersMapper.login(username,password);
        return user;
    }
}
