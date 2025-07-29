/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services.impl;

import com.cln.pojo.Student;
import com.cln.pojo.User;
import com.cln.repositories.UserRepository;
import com.cln.services.StudentService;
import com.cln.services.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author LE NGAN
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private StudentService studentService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public boolean registerUser(User user) {
        Student s = studentService.getStudentsWithoutUser(user.getStudentCode());
        if(s==null)
            return false;
        
        user.setStudent(s); 
        s.setUserId(user);
        
        
        String pass = user.getPassword();
        user.setPassword(this.passwordEncoder.encode(pass));
        user.setRole("ROLE_STUDENT");

        if (!user.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(user.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        studentService.addOrUpdateStudent(s);

        return this.userRepository.registerUser(user);
    }

    @Override
    public List<User> getUser(String usename) {
        return this.userRepository.getUser(usename);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = this.getUser(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User doesn't exit!!");
        }
        User user = users.get(0);

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);

    }

    @Override
    public List<User> getUsersByRole(String role) {
        return this.userRepository.getUsersByRole(role);
    }

    @Override
    public boolean addUser(User user) {
        String pass = user.getPassword();
        user.setPassword(this.passwordEncoder.encode(pass));
        user.setRole("ROLE_TEACHER");

        if (!user.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(user.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.userRepository.registerUser(user);
    }

}
