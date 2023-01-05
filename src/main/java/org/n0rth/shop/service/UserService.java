package org.n0rth.shop.service;

import org.n0rth.shop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean save(UserDTO userDTO);

    List<UserDTO> findAll();
}
