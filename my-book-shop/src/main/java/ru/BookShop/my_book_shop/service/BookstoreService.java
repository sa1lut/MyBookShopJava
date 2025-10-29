package ru.BookShop.my_book_shop.service;

import ru.BookShop.my_book_shop.dto.BookDto;
import ru.BookShop.my_book_shop.dto.BookItemsListDto;
import ru.BookShop.my_book_shop.dto.BookListDto;
import ru.BookShop.my_book_shop.dto.BookStoreDto;
import ru.BookShop.my_book_shop.entity.Bookstore;
import ru.BookShop.my_book_shop.entity.User;

import java.util.List;
import java.util.Optional;

public interface BookstoreService {

    void saveBookStore(Bookstore bookStore);
    void saveBookStore(BookStoreDto bookStoreDto, BookItemsListDto bookItemsListDto, BookListDto bookListDto);

    List<Bookstore> getBookstoresForUser();

    void deleteBookstore(Long id);

    Optional<Bookstore> findById(Long id);
}
