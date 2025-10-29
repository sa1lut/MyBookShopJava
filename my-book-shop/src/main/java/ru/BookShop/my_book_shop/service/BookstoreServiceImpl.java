package ru.BookShop.my_book_shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.BookShop.my_book_shop.dto.BookItemsListDto;
import ru.BookShop.my_book_shop.dto.BookListDto;
import ru.BookShop.my_book_shop.dto.BookStoreDto;
import ru.BookShop.my_book_shop.entity.BookItemsList;
import ru.BookShop.my_book_shop.entity.BookList;
import ru.BookShop.my_book_shop.entity.Bookstore;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.repository.BookRepository;
import ru.BookShop.my_book_shop.repository.BookstoreRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookstoreServiceImpl implements BookstoreService {

    private final BookstoreRepository bookstoreRepository;
    private final BookRepository bookRepository;
//    private static final Logger logger = LoggerFactory.getLogger(BookstoreService.class);

    public BookstoreServiceImpl(BookstoreRepository bookstoreRepository, BookRepository bookRepository) {
        this.bookstoreRepository = bookstoreRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void saveBookStore(Bookstore bookstore) {
        bookstoreRepository.save(bookstore);
    }

    @Override
    public void saveBookStore(BookStoreDto bookStoreDto, BookItemsListDto bookItemsListDto, BookListDto bookListDto) {
        Bookstore bookstore = new Bookstore();
        bookstore.setName(bookStoreDto.getName());
        bookstore.setAddress(bookStoreDto.getAddress());
        bookstore.setPhone(bookStoreDto.getPhone());

        BookItemsList bookItemsList = new BookItemsList();
        bookItemsList.setQuantity(bookItemsList.getQuantity());
        bookItemsList.setTotalPrice(bookItemsList.getTotalPrice());
        bookItemsList.setBook(bookItemsList.getBook());

        BookList bookList = new BookList();
        bookList.setBookstore(bookstore);

        bookstoreRepository.save(bookstore);
        bookstoreRepository.save(bookItemsList);
        bookstoreRepository.save(bookList);
    }


    @Override
    public List<Bookstore> getBookstoresForUser() {
//        if (user.getRoles().contains(Role.ADMIN)) {
//            return bookstoreRepository.findAll();
//        } else if (user.getRoles().contains(Role.USER)) {
//            return bookstoreRepository.findByCreatedBy(user);
//        } else {
//            return bookstoreRepository.findByCreatedByOrCreatedByIsNull(user);
//        }
        return bookstoreRepository.findAll();
    }

    @Override
    public void deleteBookstore(Long id) {
        bookstoreRepository.deleteById(id);
    }

    public Optional<Bookstore> findById(Long id) {
        return bookstoreRepository.findById(id);
    }
}
