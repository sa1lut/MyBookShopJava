package ru.BookShop.my_book_shop.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.BookShop.my_book_shop.dto.BookDto;
import ru.BookShop.my_book_shop.entity.Book;
import ru.BookShop.my_book_shop.entity.Role;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
//    private static final Logger logger = LoggerFactory.getLogger(BookstoreService.class);

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void saveBook(BookDto bookDto){
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());

        bookRepository.save(book);
    }

    @Override
    public BookDto createBook(BookDto bookDto, User user) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());
        book.setUserBook(user);
        Book newBook = bookRepository.save(book);
        bookDto.setTitle(newBook.getTitle());
        bookDto.setAuthor(newBook.getAuthor());
        bookDto.setPrice(newBook.getPrice());
        bookDto.setId(newBook.getId());

        return bookDto;
    }

    public List<Book> getBooksForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (user.hasRole(Role.RoleName.ROLE_USER)) {
            return bookRepository.findByUserBook(user);
        }
        return bookRepository.findAll();
    }


    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }
}
