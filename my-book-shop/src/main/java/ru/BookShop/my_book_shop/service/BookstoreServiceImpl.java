package ru.BookShop.my_book_shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.BookShop.my_book_shop.dto.BookStoreDto;
import ru.BookShop.my_book_shop.entity.Bookstore;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.repository.BookstoreRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookstoreServiceImpl implements BookstoreService {

    private final BookstoreRepository bookstoreRepository;
//    private static final Logger logger = LoggerFactory.getLogger(BookstoreService.class);

    public BookstoreServiceImpl(BookstoreRepository bookstoreRepository) {
        this.bookstoreRepository = bookstoreRepository;
    }

    @Override
    public void saveBookStore(Bookstore bookstore) {
        bookstoreRepository.save(bookstore);
    }

    @Override
    public void saveBookStore(BookStoreDto bookStoreDto) {
        Bookstore bookstore = new Bookstore();
        bookstore.setName(bookStoreDto.getName());
        bookstore.setAddress(bookStoreDto.getAddress());
        bookstore.setPhone(bookStoreDto.getPhone());
        bookstoreRepository.save(bookstore);
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
