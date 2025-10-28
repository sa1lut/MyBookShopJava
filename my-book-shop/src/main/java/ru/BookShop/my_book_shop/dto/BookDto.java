package ru.BookShop.my_book_shop.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotNull
    private Double price;
}