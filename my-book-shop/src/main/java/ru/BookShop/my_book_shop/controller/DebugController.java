package ru.BookShop.my_book_shop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DebugController {

    @GetMapping("/api/debug/auth")
    public Map<String, Object> debugAuth(Authentication authentication) {
        Map<String, Object> result = new HashMap<>();

        if (authentication != null) {
            result.put("name", authentication.getName());
            result.put("authenticated", authentication.isAuthenticated());
            result.put("authorities", authentication.getAuthorities().stream()
                    .map(a -> a.getAuthority())
                    .collect(Collectors.toList()));
        } else {
            result.put("authenticated", false);
        }

        return result;
    }
}
