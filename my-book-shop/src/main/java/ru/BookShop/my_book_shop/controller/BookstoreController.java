package ru.BookShop.my_book_shop.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ru.BookShop.my_book_shop.dto.BookDto;
import ru.BookShop.my_book_shop.dto.BookListDto;
import ru.BookShop.my_book_shop.dto.BookStoreDto;
import ru.BookShop.my_book_shop.entity.Book;
import ru.BookShop.my_book_shop.entity.Bookstore;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.repository.UserRepository;
import ru.BookShop.my_book_shop.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.BookShop.my_book_shop.service.BookstoreService;
import ru.BookShop.my_book_shop.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/bookstores")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class BookstoreController {

    private final BookstoreService bookStoreService;
    private final BookService bookService;
    private UserService userService;

    public BookstoreController(BookstoreService bookStoreService, BookService bookService, UserService userService) {
        this.bookStoreService = bookStoreService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("list")
    public ModelAndView listBookstores(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mav = new ModelAndView("bookstores-list");
        mav.addObject("stores", bookStoreService.getBookstoresForUser());
        log.info("GET /bookstores/list получение списка магазинов пользователем: {}", userDetails.getUsername());
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView showBookstoreForm(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mav = new ModelAndView("add-bookstore-form");
        mav.addObject("title", "Создать магазин");
        mav.addObject("availableBooks", bookService.getBooksForUser());
        mav.addObject("store", new BookStoreDto());
        mav.addObject("book", new Book());
        log.info("GET /bookstores/new создание магазина пользователем: {}", userDetails.getUsername());
        return mav;
    }

    @PostMapping("/saveBookStore")
    public String saveBookstore(@ModelAttribute Bookstore bookstore,
                                @ModelAttribute BookListDto bookListDto,
                                @AuthenticationPrincipal UserDetails userDetails,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("availableBooks", bookService.getBooksForUser());
            model.addAttribute("store", bookstore);
            model.addAttribute("book", bookListDto);
            return "redirect:/bookstores/new";
        }
        String username = userDetails.getUsername();
        User currentUser = userService.findUserByUsername(username);
        bookstore.setUserBookstore(currentUser);
        log.info("POST /bookstores/saveBookStore сохранение магазина: '{}' пользователем: {}", bookstore.getName(), userDetails.getUsername());
        if (bookListDto.getBookItems() == null){
            bookStoreService.saveBookStore(bookstore);
        } else {
            bookStoreService.saveBookStore(bookstore, bookListDto);
        }

        return "redirect:/bookstores/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long bookStoreId, @AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView mav = new ModelAndView("add-bookstore-form");
        Optional<Bookstore> optionalBookstore = bookStoreService.findById(bookStoreId);
        Bookstore bookStore = new Bookstore();
        if (optionalBookstore.isPresent()) {
            bookStore = optionalBookstore.get();
        }
        log.info("GET /bookstores/showUpdateForm открыта форма для редактирования магазина: '{}' пользователем: {}", bookStore.getName(), userDetails.getUsername());
        List<BookDto> bookDtos = bookStoreService.getBooksWithQuantity(bookStore.getId());
        Double totalPrice = bookStoreService.getTotalPrice(bookDtos);
        Integer totalQuantity = bookStoreService.getTotalQuantity(bookDtos);

        BookStoreDto bookStoreDto = new BookStoreDto(bookStore.getId(), bookStore.getName(),
                bookStore.getAddress(), bookStore.getPhone(), totalPrice,
                totalQuantity, bookDtos);


        mav.addObject("title", "Изменить магазин");
        mav.addObject("store", bookStoreDto);

        mav.addObject("availableBooks", bookService.getBooksForUser());
        mav.addObject("book", new Book());

        return mav;
    }

    @GetMapping("/deleteBookStore")
    public String deleteBook(@RequestParam Long bookStoreId, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET /bookstores/deleteBookStore удаление магазина пользователем: {}", userDetails.getUsername());
        bookStoreService.deleteBookstore(bookStoreId);
        return "redirect:/bookstores/list";
    }
}