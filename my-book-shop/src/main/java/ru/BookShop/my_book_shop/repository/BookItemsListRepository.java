package ru.BookShop.my_book_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.BookShop.my_book_shop.entity.Book;
import ru.BookShop.my_book_shop.entity.BookItemsList;

@Repository
public interface BookItemsListRepository extends JpaRepository<BookItemsList, Long> {
}