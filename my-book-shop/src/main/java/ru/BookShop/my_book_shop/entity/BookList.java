package ru.BookShop.my_book_shop.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bookList")
public class BookList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double totalPrice;

    @OneToMany(mappedBy = "booklist")
    private List<Book> books = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "book_store_id")
    private Bookstore bookstore;
}
