package ru.BookShop.my_book_shop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.BookShop.my_book_shop.entity.Bookstore;
import ru.BookShop.my_book_shop.entity.Role;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.repository.BookstoreRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookstoreService {

    private final BookstoreRepository bookstoreRepository;
//    private static final Logger logger = LoggerFactory.getLogger(BookstoreService.class);

    public BookstoreService(BookstoreRepository bookstoreRepository) {
        this.bookstoreRepository = bookstoreRepository;
    }

    public List<Bookstore> getBookstoresForUser(User user) {
//        if (user.getRoles().contains(Role.ADMIN)) {
//            return bookstoreRepository.findAll();
//        } else if (user.getRoles().contains(Role.USER)) {
//            return bookstoreRepository.findByCreatedBy(user);
//        } else {
//            return bookstoreRepository.findByCreatedByOrCreatedByIsNull(user);
//        }
        return List.of();
    }

    public void deleteBookstore(Long id, User user) {
        Bookstore bookstore = bookstoreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bookstore not found"));

//        if (user.getRoles().contains(Role.ADMIN) ||
//                bookstore.getCreatedBy().getId().equals(user.getId())) {
//            bookstoreRepository.deleteById(id);
////            logger.info("Bookstore deleted: {} by user {}", bookstore.getName(), user.getUsername());
//        } else {
//            throw new RuntimeException("Access denied");
//        }
    }

    public Optional<Bookstore> findById(Long id) {
        return bookstoreRepository.findById(id);
    }
}
