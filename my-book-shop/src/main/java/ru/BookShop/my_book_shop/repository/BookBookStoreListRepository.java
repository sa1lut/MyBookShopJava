package ru.BookShop.my_book_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.BookShop.my_book_shop.entity.BookBookStoreList;

@Repository
public interface BookBookStoreListRepository extends JpaRepository<BookBookStoreList, Long> {
    @Modifying
    @Query(value = "DELETE * FROM bookBookStoreList WHERE id = :id", nativeQuery = true)
    void hardDeleteById(@Param("id") Long id);
}