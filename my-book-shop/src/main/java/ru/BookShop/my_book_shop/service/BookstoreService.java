package ru.BookShop.my_book_shop.service;

import ru.BookShop.my_book_shop.dto.BookDto;
import ru.BookShop.my_book_shop.dto.BookListDto;
import ru.BookShop.my_book_shop.entity.Bookstore;

import java.util.List;
import java.util.Optional;

public interface BookstoreService {

    void saveBookStore(Bookstore bookstore);

    void saveBookStore(Bookstore bookstore, BookListDto bookListDto);

    List<Bookstore> getBookstoresForUser();

//    BookItemsList getByBookStoreId(Long id);

    void deleteBookstore(Long id);

    Optional<Bookstore> findById(Long id);

    List<BookDto> getBooksWithQuantity(Long bookStoreId);

    Integer getTotalQuantity(List<BookDto> bookDtos);

    Double getTotalPrice(List<BookDto> bookDtos);
}
