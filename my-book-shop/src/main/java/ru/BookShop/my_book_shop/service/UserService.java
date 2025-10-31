package ru.BookShop.my_book_shop.service;
import ru.BookShop.my_book_shop.dto.UserDto;
import ru.BookShop.my_book_shop.dto.UserUpdateDto;
import ru.BookShop.my_book_shop.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    void saveUser(UserDto userDto);

    List<UserDto> findAllUsers();

    User findUserByUsername(String username);

    User findUserById(Long id);

    void updateUser(UserUpdateDto user, Set<String> roleNames);

    void deleteUser(Long id);
}