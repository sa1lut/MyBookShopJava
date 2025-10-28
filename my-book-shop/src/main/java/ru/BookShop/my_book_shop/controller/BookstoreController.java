package ru.BookShop.my_book_shop.controller;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ru.BookShop.my_book_shop.dto.BookStoreDto;
import ru.BookShop.my_book_shop.entity.Book;
import ru.BookShop.my_book_shop.entity.Bookstore;
import ru.BookShop.my_book_shop.repository.UserRepository;
import ru.BookShop.my_book_shop.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.BookShop.my_book_shop.service.BookstoreService;

import java.util.Optional;

@Controller
@RequestMapping("/bookstores")
public class BookstoreController {

    private final BookstoreService bookStoreService;
    private final BookService bookService;
    private final UserRepository userRepository;

    public BookstoreController(BookstoreService bookStoreService, BookService bookService, UserRepository userRepository) {
        this.bookStoreService = bookStoreService;
        this.bookService = bookService;
        this.userRepository = userRepository;
    }

    @GetMapping("list")
    public ModelAndView listBookstores() {
        ModelAndView mav = new ModelAndView("bookstores-list");
        mav.addObject("stores", bookStoreService.getBookstoresForUser());
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView showBookstoreForm(Model model) {
        ModelAndView mav = new ModelAndView("add-bookstore-form");
        mav.addObject("title", "Создать магазин");
        mav.addObject("availableBooks", bookService.getBooksForUser());
        mav.addObject("bookstore", new Bookstore());
        mav.addObject("book", new Book());
        return mav;
    }

    @PostMapping("/saveBookStore")
    public String saveBookstore(@Valid @ModelAttribute("bookStore") BookStoreDto bookStoreDtoDto,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("bookStore", bookStoreDtoDto);
            return "redirect:/bookstores/new";
        }
        bookStoreService.saveBookStore(bookStoreDtoDto);
        return "redirect:/bookstores/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long bookStoreId) {
        ModelAndView mav = new ModelAndView("add-bookstore-form");
        Optional<Bookstore> optionalBookstore = bookStoreService.findById(bookStoreId);
        Bookstore bookStore = new Bookstore();
        if (optionalBookstore.isPresent()) {
            bookStore = optionalBookstore.get();
        }
        mav.addObject("title", "Изменить магазин");
        mav.addObject("bookStore", bookStore);

        return mav;
    }

    @GetMapping("/deleteBookStore")
    public String deleteBook(@RequestParam Long bookStoreId) {
        bookStoreService.deleteBookstore(bookStoreId);
        return "redirect:/bookstores/list";
    }
}