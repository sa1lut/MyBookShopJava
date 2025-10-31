package ru.BookShop.my_book_shop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.BookShop.my_book_shop.dto.UserDto;
import ru.BookShop.my_book_shop.dto.UserUpdateDto;
import ru.BookShop.my_book_shop.entity.Role;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.repository.RoleRepository;
import ru.BookShop.my_book_shop.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        //encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setRoles(Set.of(
                roleRepository.findByName(Role.RoleName.ROLE_READ_ONLY)
        ));
        userRepository.save(user);
        log.info("Сохранен пользователь: {}", user.getUsername());
    }

    @Override
    public void updateUser(UserUpdateDto user, Set<String> roleNames) {
        User existingUser = findUserByUsername(user.getUsername());

        // Обновляем основные поля
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        // Получаем существующие роли из базы данных
        Set<Role> newRoles = roleNames.stream()
                .map(roleName -> {
                    // Ищем роль в базе данных по имени
                    Role role = roleRepository.findByName(Role.RoleName.valueOf(roleName));
                    return role;
                })
                .collect(Collectors.toSet());

        existingUser.setRoles(newRoles);
        userRepository.save(existingUser);
        log.info("Изменен пользователь: {}", user.getUsername());
    }

    @Override
    public User findUserById(Long id){
        log.info("Поиск по: {}", id);
        return userRepository.getById(id);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Удаление пользователя с id: {}", userId);
        userRepository.deleteById(userId);
    }

    @Override
    public User findUserByUsername(String username) {
        log.info("Поиск по: {}", username);
        return userRepository.findByUsername(username);
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
