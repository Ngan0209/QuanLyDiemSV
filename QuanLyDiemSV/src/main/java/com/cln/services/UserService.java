/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services;

import com.cln.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author LE NGAN
 */
public interface UserService extends UserDetailsService{
    boolean registerUser(User user);
    List<User> getUser(String usename);
    User getUserByUsername(String username);
    List<User> getUsersByRole(String role);
    boolean addUser(User user);
    boolean authenticate(String username, String password);
    User studentRegister(Map<String, String> params, MultipartFile avatar);
}
