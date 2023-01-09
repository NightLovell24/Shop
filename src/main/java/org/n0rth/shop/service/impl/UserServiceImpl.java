package org.n0rth.shop.service.impl;

import org.n0rth.shop.dao.UserRepository;
import org.n0rth.shop.domain.Role;
import org.n0rth.shop.domain.User;
import org.n0rth.shop.dto.UserDTO;
import org.n0rth.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public boolean save(UserDTO userDTO) {
        if (!userDTO.getMatchingPassword().equals(userDTO.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        User user = User.builder().name(userDTO.getUsername()).
                password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with name :" + username);
        }
        List<GrantedAuthority> roles = new ArrayList<>();

        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getName(),
                user.getPassword(), roles);


    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateProfile(UserDTO dto) {
        User user = userRepository.findByName(dto.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found by name " + dto.getUsername());
        }
        boolean isChanged = false;
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            isChanged = true;
        }
        if (!Objects.equals(dto.getEmail(), user.getEmail())
        ) {
            user.setEmail(dto.getEmail());
            isChanged = true;
        }
        if (!Objects.equals(dto.getPhone(), user.getPhone())) {
            user.setPhone(dto.getPhone());
            isChanged = true;
        }
        if (isChanged) {
            userRepository.save(user);
        }
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone()).build();
    }
}
