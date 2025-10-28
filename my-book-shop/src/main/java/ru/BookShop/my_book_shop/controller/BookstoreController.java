package ru.BookShop.my_book_shop.controller;

import ru.BookShop.my_book_shop.entity.Bookstore;
import ru.BookShop.my_book_shop.entity.Role;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.repository.UserRepository;
import ru.BookShop.my_book_shop.service.BookstoreService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookstores")
public class BookstoreController {

    private final BookstoreService bookstoreService;
    private final UserRepository userRepository;

    public BookstoreController(BookstoreService bookstoreService, UserRepository userRepository) {
        this.bookstoreService = bookstoreService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listBookstores(Model model, Authentication authentication) {
//        User user = getUserFromAuthentication(authentication);
//        model.addAttribute("bookstores", bookstoreService.getBookstoresForUser(user));
//        model.addAttribute("isAdmin", hasRole(authentication, Role.ADMIN));
//        model.addAttribute("isUser", hasRole(authentication, Role.USER));
        return "bookstore-list";
    }

    @GetMapping("/new")
    public String showBookstoreForm(Model model) {
        model.addAttribute("bookstore", new Bookstore());
        return "bookstore-form";
    }

    @PostMapping
    public String saveBookstore(@ModelAttribute Bookstore bookstore, Authentication authentication) {
//        User user = getUserFromAuthentication(authentication);
//        bookstoreService.saveBookstore(bookstore, user);
        return "redirect:/bookstores";
    }

    @GetMapping("/{id}/edit")
    public String editBookstore(@PathVariable Long id, Model model, Authentication authentication) {
        Bookstore bookstore = bookstoreService.findById(id)
                .orElseThrow(() -> new RuntimeException("Bookstore not found"));

//        User user = getUserFromAuthentication(authentication);
//        if (!hasRole(authentication, Role.ADMIN) &&
//                !bookstore.getCreatedBy().getId().equals(user.getId())) {
//            throw new RuntimeException("Access denied");
//        }

        model.addAttribute("bookstore", bookstore);
        return "bookstore-form";
    }

    @PostMapping("/{id}/delete")
    public String deleteBookstore(@PathVariable Long id, Authentication authentication) {
//        User user = getUserFromAuthentication(authentication);
//        bookstoreService.deleteBookstore(id, user);
        return "redirect:/bookstores";
    }

//    private User getUserFromAuthentication(Authentication authentication) {
//        String username = authentication.getName();
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }

//    private boolean hasRole(Authentication authentication, Role role) {
//        return authentication.getAuthorities().stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role.name()));
//    }
}