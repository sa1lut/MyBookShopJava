package ru.BookShop.my_book_shop.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.BookShop.my_book_shop.dto.BookDto;
import ru.BookShop.my_book_shop.dto.UserDto;
import ru.BookShop.my_book_shop.entity.Book;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.repository.BookRepository;
import ru.BookShop.my_book_shop.service.BookService;
import ru.BookShop.my_book_shop.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/books")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class BookController {
    private BookService bookService;
    private UserService userService;
    public BookController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/list")
    public ModelAndView getAllBooks(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mav = new ModelAndView("book-list");
        mav.addObject("books", bookService.getBooksForUser());
        log.info("GET /books/list получение списка книг пользователем: {}", userDetails.getUsername());
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView showBookForm(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mav = new ModelAndView("add-book-form");
        mav.addObject("title", "Добавить книгу");
        mav.addObject("book", new Book());
        log.info("GET /books/new создание новой книги пользователем: {}", userDetails.getUsername());
        return mav;
    }

    @PostMapping("/saveBook")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           @AuthenticationPrincipal UserDetails userDetails,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book", book);
            return "redirect:/books/new";
        }
        log.info("POST /books/saveBook сохранение книги пользователем: {}", userDetails.getUsername());
        String username = userDetails.getUsername();
        User currentUser = userService.findUserByUsername(username);
        book.setUserBook(currentUser);
        bookService.saveBook(book);
        return "redirect:/books/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long bookId, @AuthenticationPrincipal UserDetails userDetails) {

        ModelAndView mav = new ModelAndView("add-book-form");
        Optional<Book> optionalBook = bookService.findById(bookId);
        Book book = new Book();
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
        }
        mav.addObject("title", "Изменить книгу");
        mav.addObject("book", book);
        log.info("GET /books/showUpdateForm открыта форма для редактирования книги пользователем: {}", userDetails.getUsername());
        return mav;
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam Long bookId, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET /books/deleteBook удаление книги пользователем: {}", userDetails.getUsername());
        bookService.deleteBook(bookId);
        return "redirect:/books/list";
    }

    @PostMapping("/create-ajax")
    @ResponseBody
    public ResponseEntity<?> createBookAjax(@Valid @RequestBody BookDto bookDto,
                                            @AuthenticationPrincipal UserDetails userDetails,
                                            BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            String username = userDetails.getUsername();
            log.info("POST /books/create-ajax быстрое сохранение книги пользователем: {}", userDetails.getUsername());
            User currentUser = userService.findUserByUsername(username);
            BookDto savedBook = bookService.createBook(bookDto, currentUser);
            return ResponseEntity.ok(savedBook);
        } catch (Exception e) {
            log.error("POST /books/create-ajax при быстром сохранение книги пользователем: {}", userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Ошибка при создании книги: " + e.getMessage()));
        }
    }
}
