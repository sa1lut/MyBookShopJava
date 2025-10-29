package ru.BookShop.my_book_shop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.BookShop.my_book_shop.entity.Book;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemsListDto {
    private Long bookId;
    @NotNull
    private Double price;
    @NotNull
    private Integer quantity;

    @Override
    public String toString() {
        return "BookItemRequest{" +
                "bookId=" + bookId +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
