package ru.BookShop.my_book_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.BookShop.my_book_shop.entity.Bookstore;
import ru.BookShop.my_book_shop.entity.User;

import java.util.List;

@Repository
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {
    List<Bookstore> findByUserBookstore(User user);
}
