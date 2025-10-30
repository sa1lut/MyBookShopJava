package ru.BookShop.my_book_shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleName name;

    public enum RoleName {
        ROLE_ADMIN,
        ROLE_USER,
        ROLE_READ_ONLY
    }

    @Override
    public String getAuthority() {
        return name.name(); // Возвращает "ROLE_ADMIN", "ROLE_USER" и т.д.
    }
}
