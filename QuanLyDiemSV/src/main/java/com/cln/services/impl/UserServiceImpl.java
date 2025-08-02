/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.services.impl;

import com.cln.pojo.Student;
import com.cln.pojo.Teacher;
import com.cln.pojo.User;
import com.cln.repositories.UserRepository;
import com.cln.services.StudentService;
import com.cln.services.TeacherSevice;
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
import org.springframework.web.multipart.MultipartFile;

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
    private TeacherSevice teacherService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public boolean registerUser(User user) {
        Student s = studentService.getStudentsWithoutUser(user.getStudentCode());
        if (s == null) {
            return false;
        }

        if (!user.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(user.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String pass = user.getPassword();
        user.setPassword(this.passwordEncoder.encode(pass));
        user.setRole("ROLE_STUDENT");

        user.setStudent(s);
        s.setUserId(user);

        boolean result = this.userRepository.registerUser(user);
        if (!result) {
            return false;
        }

        studentService.addOrUpdateStudent(s);

        return true;
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
        Long teacherId = user.getTeacher().getId();
        Teacher t = this.teacherService.getTeacherById(teacherId);

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

        boolean success = this.userRepository.registerUser(user);

        if (success) {
            t.setUserId(user);
            teacherService.addOrUpdateTeacher(t);
        }

        return success;
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepository.authenticate(username, password);
    }

    @Override
    public User studentRegister(Map<String, String> params, MultipartFile avatar) {
        User u = new User();
        u.setFirstName(params.get("firstName"));
        u.setLastName(params.get("lastName"));
        u.setEmail(params.get("email"));
        u.setUsername(params.get("username"));
        u.setStudentCode(params.get("studentCode"));
        u.setPassword(this.passwordEncoder.encode(params.get("password")));
        u.setRole("ROLE_STUDENT");

        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Student s = studentService.getStudentsWithoutUser(u.getStudentCode());

        if (s == null) {
            throw new RuntimeException("Student code không hợp lệ hoặc đã có tài khoản!");
        }

        // Gán user cho student
        s.setUserId(u);
        u.setStudent(s);

        u = this.userRepository.studentRegister(u);
        
        studentService.addOrUpdateStudent(s);
        
        return u;
    }

}
