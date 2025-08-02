/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.repositories;

import com.cln.pojo.User;
import java.util.List;

/**
 *
 * @author LE NGAN
 */
public interface UserRepository {
    boolean registerUser(User user);
    List<User> getUser(String usename);
    User getUserByUsername(String username);
    List<User> getUsersByRole(String role);
    boolean authenticate(String username, String password);
    User studentRegister(User user);
}
