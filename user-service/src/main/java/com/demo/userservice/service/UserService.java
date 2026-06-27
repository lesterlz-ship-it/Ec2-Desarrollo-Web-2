package com.demo.userservice.service;

import com.demo.userservice.dto.UserCreateRequest;
import com.demo.userservice.dto.UserDTO;
import com.demo.userservice.dto.UserUpdateRequest;
import com.demo.userservice.entity.User;
import com.demo.userservice.exception.DuplicateResourceException;
import com.demo.userservice.exception.ResourceNotFoundException;
import com.demo.userservice.mapper.UserMapper;
import com.demo.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> findAll() {
        log.info("Listando todos los usuarios");
        return userMapper.toDTOList(userRepository.findAll());
    }

    public UserDTO findById(Long id) {
        log.info("Buscando usuario con id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        return userMapper.toDTO(user);
    }

    public UserDTO create(UserCreateRequest request) {
        log.info("Creando usuario con email {}", request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Ya existe un usuario con el email: " + request.getEmail());
        }
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        log.info("Usuario creado con id {}", saved.getId());
        return userMapper.toDTO(saved);
    }

    public UserDTO update(Long id, UserUpdateRequest request) {
        log.info("Actualizando usuario con id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));

        if (request.getEmail() != null
                && !request.getEmail().equals(user.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Ya existe otro usuario con el email: " + request.getEmail());
        }

        userMapper.updateEntityFromRequest(request, user);
        User updated = userRepository.save(user);
        log.info("Usuario {} actualizado", id);
        return userMapper.toDTO(updated);
    }

    public void delete(Long id) {
        log.info("Eliminando usuario con id {}", id);
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", id);
        }
        userRepository.deleteById(id);
        log.info("Usuario {} eliminado", id);
    }
}
