package org.gec.service;


import org.gec.pojo.Users;

public interface UsersService {
    Users Login(String username, String password);
}
