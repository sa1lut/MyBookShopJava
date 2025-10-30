package ru.BookShop.my_book_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.BookShop.my_book_shop.dto.BookDto;
import ru.BookShop.my_book_shop.entity.Book;
import ru.BookShop.my_book_shop.entity.User;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByUserBook(User user);
}