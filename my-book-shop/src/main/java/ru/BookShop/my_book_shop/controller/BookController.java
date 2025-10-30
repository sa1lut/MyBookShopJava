package ru.BookShop.my_book_shop.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.BookShop.my_book_shop.dto.BookDto;
import ru.BookShop.my_book_shop.dto.UserDto;
import ru.BookShop.my_book_shop.entity.Book;
import ru.BookShop.my_book_shop.repository.BookRepository;
import ru.BookShop.my_book_shop.service.BookService;
import ru.BookShop.my_book_shop.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private BookService bookService;
    private UserService userService;
    public BookController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }


    @GetMapping("/list")
    public ModelAndView getAllBooks() {
        ModelAndView mav = new ModelAndView("book-list");
        mav.addObject("books", bookService.getBooksForUser());
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView showBookForm() {
        ModelAndView mav = new ModelAndView("add-book-form");
        mav.addObject("title", "Добавить книгу");
        mav.addObject("book", new Book());

        return mav;
    }

    @PostMapping("/saveBook")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book", book);
            return "redirect:/books/new";
        }
        bookService.saveBook(book);
        return "redirect:/books/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long bookId) {

        ModelAndView mav = new ModelAndView("add-book-form");
        Optional<Book> optionalBook = bookService.findById(bookId);
        Book book = new Book();
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
        }
        mav.addObject("title", "Изменить книгу");
        mav.addObject("book", book);

        return mav;
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam Long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/books/list";
    }

    @PostMapping("/create-ajax")
    @ResponseBody
    public ResponseEntity<?> createBookAjax(@Valid @RequestBody BookDto bookDto,
                                            BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            BookDto savedBook = bookService.createBook(bookDto);
            return ResponseEntity.ok(savedBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Ошибка при создании книги: " + e.getMessage()));
        }
    }
}
