//package com.example.movielist.service;
//
//import com.example.movielist.model.User;
//import com.example.movielist.repository.UserRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Transactional
//    public User register(String login, String password) {
//        if (userRepository.existsByLogin(login)) {
//            throw new IllegalArgumentException("Пользователь с таким логином уже существует");
//        }
//        User user = new User(login, passwordEncoder.encode(password));
//        return userRepository.save(user);
//    }
//
//    public User findByLogin(String login) {
//        return userRepository.findByLogin(login)
//                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
//    }
//}
