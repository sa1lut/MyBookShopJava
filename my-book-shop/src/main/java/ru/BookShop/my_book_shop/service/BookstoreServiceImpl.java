package ru.BookShop.my_book_shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.BookShop.my_book_shop.dto.BookItemsListDto;
import ru.BookShop.my_book_shop.dto.BookListDto;
import ru.BookShop.my_book_shop.dto.BookStoreDto;
import ru.BookShop.my_book_shop.entity.Book;
import ru.BookShop.my_book_shop.entity.BookItemsList;
import ru.BookShop.my_book_shop.entity.BookList;
import ru.BookShop.my_book_shop.entity.Bookstore;
import ru.BookShop.my_book_shop.repository.BookItemsListRepository;
import ru.BookShop.my_book_shop.repository.BookListRepository;
import ru.BookShop.my_book_shop.repository.BookRepository;
import ru.BookShop.my_book_shop.repository.BookstoreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookstoreServiceImpl implements BookstoreService {

    private final BookstoreRepository bookstoreRepository;
    private final BookRepository bookRepository;
    private final BookItemsListRepository bookItemsListRepository;
    private final BookListRepository bookListRepository;
//    private static final Logger logger = LoggerFactory.getLogger(BookstoreService.class);

    public BookstoreServiceImpl(BookstoreRepository bookstoreRepository, BookRepository bookRepository,
                                BookItemsListRepository bookItemsListRepository, BookListRepository bookListRepository) {
        this.bookstoreRepository = bookstoreRepository;
        this.bookRepository = bookRepository;
        this.bookItemsListRepository = bookItemsListRepository;
        this.bookListRepository = bookListRepository;
    }

    @Override
    public void saveBookStore(BookStoreDto bookStoreDto) {
        Bookstore bookstore = new Bookstore();
        bookstore.setName(bookStoreDto.getName());
        bookstore.setPhone(bookStoreDto.getPhone());
        bookstore.setAddress(bookStoreDto.getAddress());
        bookstoreRepository.save(bookstore);
    }

    @Override
    public void saveBookStore(BookStoreDto bookStoreDto, BookListDto bookListDto) {
        Bookstore bookstore = new Bookstore();
        bookstore.setName(bookStoreDto.getName());
        bookstore.setAddress(bookStoreDto.getAddress());
        bookstore.setPhone(bookStoreDto.getPhone());

        BookList bookList = new BookList();
        bookList.setBookstore(bookstore);
        bookList.setTotalPrice(bookListDto.getTotalPrice());

        List<Book> books = new ArrayList<>();
        List<BookItemsList> bookItemsLists = new ArrayList<>();
        for (BookItemsListDto bookItemList : bookListDto.getBookItems()) {
            BookItemsList bookItemListNew = new BookItemsList();
            bookItemListNew.setQuantity(bookItemList.getQuantity());
            bookItemListNew.setTotalPrice(bookItemList.getPrice());
            Optional<Book> optionalBook = bookRepository.findById(bookItemList.getBookId());
            Book book = new Book();

            if (optionalBook.isPresent()) {
                book = optionalBook.get();
            }
            books.add(book);
            bookItemListNew.setBooks(books);
            bookItemListNew.setBookList(bookList);
            bookItemsLists.add(bookItemListNew);
            book.setBookItemsLists(bookItemsLists);
            bookRepository.save(book);
            bookItemsListRepository.save(bookItemListNew);
        }

        bookstoreRepository.save(bookstore);
        bookListRepository.save(bookList);
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
