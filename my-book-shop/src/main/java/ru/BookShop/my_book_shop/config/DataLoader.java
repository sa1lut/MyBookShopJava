package ru.BookShop.my_book_shop.config;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.BookShop.my_book_shop.entity.Role;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.repository.RoleRepository;
import ru.BookShop.my_book_shop.repository.UserRepository;

import java.util.Set;

@Component
public class DataLoader {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RoleRepository roleRepository,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void loadData() {
        createRoles();
        createUsers();
    }

    private void createRoles() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName(Role.RoleName.ROLE_ADMIN);
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName(Role.RoleName.ROLE_USER);
            roleRepository.save(userRole);

            Role readOnlyRole = new Role();
            readOnlyRole.setName(Role.RoleName.ROLE_READ_ONLY);
            roleRepository.save(readOnlyRole);
        }
    }

    private void createUsers() {
        if (userRepository.count() == 0) {
            // ADMIN пользователь
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));

            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setRoles(Set.of(
                    roleRepository.findByName(Role.RoleName.ROLE_ADMIN)
            ));
            userRepository.save(admin);

            // Обычный пользователь
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));

            user.setFirstName("Regular");
            user.setLastName("User");
            user.setRoles(Set.of(
                    roleRepository.findByName(Role.RoleName.ROLE_USER)
            ));
            userRepository.save(user);

            // ReadOnly пользователь
            User readOnlyUser = new User();
            readOnlyUser.setUsername("reader");
            readOnlyUser.setPassword(passwordEncoder.encode("reader123"));

            readOnlyUser.setFirstName("Read");
            readOnlyUser.setLastName("Only");
            readOnlyUser.setRoles(Set.of(
                    roleRepository.findByName(Role.RoleName.ROLE_READ_ONLY)
            ));
            userRepository.save(readOnlyUser);
        }
    }
}
