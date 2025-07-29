/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services;

import com.cln.pojo.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author LE NGAN
 */
public interface UserService extends UserDetailsService{
    boolean registerUser(User user);
    List<User> getUser(String usename);
    List<User> getUsersByRole(String role);
    boolean addUser(User user);
}
