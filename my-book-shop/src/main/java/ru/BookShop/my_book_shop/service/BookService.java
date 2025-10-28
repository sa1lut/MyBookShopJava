package ru.BookShop.my_book_shop.service;

import ru.BookShop.my_book_shop.dto.BookDto;
import ru.BookShop.my_book_shop.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void saveBook(Book book);
    void saveBook(BookDto bookDto);
    List<Book> getBooksForUser();
    Optional<Book> findById(Long id);
    void deleteBook(Long id);
}
