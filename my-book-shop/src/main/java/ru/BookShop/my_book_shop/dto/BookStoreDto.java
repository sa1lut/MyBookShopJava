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
public class BookStoreDto {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotNull
    private String phone;

    @Override
    public String toString() {
        return "BookStoreRequest{" +
                "name=" + name +
                ", address=" + address +
                ", phone=" + phone +
                '}';
    }
}