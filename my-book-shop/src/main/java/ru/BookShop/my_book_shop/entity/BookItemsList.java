package ru.BookShop.my_book_shop.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bookItemsList")
public class BookItemsList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double totalPrice;

    @ManyToMany
    private List<Book> books = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "bookLists")
    private BookList bookList;
}
