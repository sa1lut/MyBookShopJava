package ru.BookShop.my_book_shop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookListDto {
    private Long id;

    @NotNull
    private Double totalPrice;

    private List<BookItemsListDto> bookItems;

    private List<Long> deletedBookItems;

    @Override
    public String toString() {
        return "BookListRequest{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", items=" + bookItems +
                ", ids=" + deletedBookItems +
                '}';
    }
}

