package ru.BookShop.my_book_shop.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) { this.userRepository = userRepository; }


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(usernameOrEmail);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority(user.getRole().getName())));
        }

        throw new UsernameNotFoundException("Invalid email or password");
    }
}
