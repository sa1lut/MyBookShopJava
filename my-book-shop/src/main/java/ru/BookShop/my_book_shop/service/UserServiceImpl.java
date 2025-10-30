package ru.BookShop.my_book_shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.BookShop.my_book_shop.dto.UserDto;
import ru.BookShop.my_book_shop.entity.Role;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.repository.RoleRepository;
import ru.BookShop.my_book_shop.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
//        Role role = roleRepository.findByName("ROLE_ADMIN");
//        if (role == null) {
//            role = checkRoleExist();
//        }
//
//        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String username) {
        return userRepository.findByUsername(username);
    }

//    private Role checkRoleExist() {
//        Role role = new Role();
//        role.setName("ROLE_ADMIN");
//        return roleRepository.save(role);
//    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
//        String[] str = user.getUsername().split(" ");
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
}
