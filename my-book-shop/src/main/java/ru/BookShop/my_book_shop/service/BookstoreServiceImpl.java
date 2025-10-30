package ru.BookShop.my_book_shop.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.BookShop.my_book_shop.dto.BookDto;
import ru.BookShop.my_book_shop.dto.BookItemsListDto;
import ru.BookShop.my_book_shop.dto.BookListDto;
import ru.BookShop.my_book_shop.entity.*;
import ru.BookShop.my_book_shop.repository.BookBookStoreListRepository;
import ru.BookShop.my_book_shop.repository.BookRepository;
import ru.BookShop.my_book_shop.repository.BookstoreRepository;

import java.util.*;

@Service
@Transactional
public class BookstoreServiceImpl implements BookstoreService {

    private final BookstoreRepository bookstoreRepository;
    private final BookRepository bookRepository;
    private final BookBookStoreListRepository bookBookStoreListRepository;
//    private static final Logger logger = LoggerFactory.getLogger(BookstoreService.class);

    public BookstoreServiceImpl(BookstoreRepository bookstoreRepository, BookRepository bookRepository,
                                BookBookStoreListRepository bookBookStoreListRepository) {
        this.bookstoreRepository = bookstoreRepository;
        this.bookRepository = bookRepository;
        this.bookBookStoreListRepository = bookBookStoreListRepository;
    }

    @Override
    public void saveBookStore(Bookstore bookstore) {
        bookstoreRepository.save(bookstore);
    }

    @Override
    public void saveBookStore(Bookstore bookstore, BookListDto bookListDto) {
        try {
            for (Long delId : bookListDto.getDeletedBookItems()) {
                bookBookStoreListRepository.deleteById(delId);
                System.out.println("Deleted!");
            }
        } catch (Exception _){}

        bookstoreRepository.save(bookstore);

        for (BookItemsListDto bookItemList : bookListDto.getBookItems()) {
            // Создаем новый ТОЛЬКО если ID null, иначе используем существующий
            BookBookStoreList bookItemListNew = bookItemList.getBookItemId() != null
                    ? bookBookStoreListRepository
                    .findById(bookItemList.getBookItemId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "BookBookStoreList not found with id: " + bookItemList.getBookItemId()
                    ))
                    : new BookBookStoreList();

            bookItemListNew.setQuantity(bookItemList.getQuantity());
            bookItemListNew.setBook(bookRepository.findById(bookItemList.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Book not found")));
            bookItemListNew.setBookstore(bookstore);

            bookBookStoreListRepository.save(bookItemListNew);
        }
    }


    @Override
    public List<Bookstore> getBookstoresForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (user.hasRole(Role.RoleName.ROLE_USER)) {
            return bookstoreRepository.findByUserBookstore(user);
        }
        return bookstoreRepository.findAll();
    }

    @Override
    public void deleteBookstore(Long id) {
        bookstoreRepository.deleteById(id);
    }

    public Optional<Bookstore> findById(Long id) {
        return bookstoreRepository.findById(id);
    }

//    public BookItemsList getByBookStoreId(Long id) {
//
//        BookList bookList = null;
//        BookItemsList bookItemsList = null;
//        try {
//            bookList = bookListRepository.getReferenceById(id);
//            if (!bookListRepository.existsById(id)) {
//                return null;
//            }
//            Long idBookList = bookList.getId();
//            bookItemsList = bookItemsListRepository.getReferenceById(idBookList);
//            return bookItemsList;
//        } catch (EntityNotFoundException e) {
//            return null;
//        }
//    }

    public List<BookDto> getBooksWithQuantity(Long bookStoreId) {
        // Получаем информацию о книжном магазине с количеством
        Bookstore bookStore = bookstoreRepository.getById(bookStoreId);

        // Получаем список книг с их количеством
        List<BookDto> bookDto = new ArrayList<>();

        // Предполагая, что у BookStore есть метод getBookQuantities()
        // который возвращает Map<Book, Integer> или List<BookQuantity>
        for (BookBookStoreList bookBookStoreList : bookStore.getBookBookStoreListSet()) {
            Integer quantity = bookBookStoreList.getQuantity(); // или аналогичный метод
            Book book = bookBookStoreList.getBook();
            BookDto bookWithQuantity = new BookDto(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPrice(),
                    quantity,
                    bookBookStoreList.getId()
            );
            bookDto.add(bookWithQuantity);
        }

        return bookDto;
    }

    public Double getTotalPrice(List<BookDto> bookDtos) {
        Double totalPrice = 0.0;
        for (BookDto bookDto : bookDtos) {
            totalPrice += bookDto.getPrice();
        }

        return  totalPrice;
    }

    public Integer getTotalQuantity(List<BookDto> bookDtos) {
        Integer totalQuantity = 0;

        for (BookDto bookDto : bookDtos) {
            totalQuantity += bookDto.getQuantity();
        }

        return totalQuantity;
    }
}
