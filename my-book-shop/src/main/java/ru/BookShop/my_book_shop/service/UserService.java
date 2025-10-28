package ru.BookShop.my_book_shop.service;
import ru.BookShop.my_book_shop.dto.UserDto;
import ru.BookShop.my_book_shop.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    void saveUser(User user);

    User findUserByEmail(String username);

    List<UserDto> findAllUsers();
}