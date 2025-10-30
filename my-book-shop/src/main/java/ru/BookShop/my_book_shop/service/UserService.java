package ru.BookShop.my_book_shop.service;
import ru.BookShop.my_book_shop.dto.UserDto;
import ru.BookShop.my_book_shop.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserDto userDto);
    void saveUser(User user);

    User findUserByEmail(String username);

    List<UserDto> findAllUsers();

    User findUserByUsername(String username);
}