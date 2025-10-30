package ru.BookShop.my_book_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.BookShop.my_book_shop.entity.Bookstore;

@Repository
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {
//    void save(BookItemsList bookItemsList);
//    void save(BookList bookList);
}
