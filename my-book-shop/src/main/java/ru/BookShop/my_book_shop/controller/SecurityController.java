package ru.BookShop.my_book_shop.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.BookShop.my_book_shop.dto.UserDto;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.service.UserService;

import java.util.List;

@Controller
public class SecurityController {
    private UserService userService;
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

//    @GetMapping("/books")
//    public String books() {
//        return "books";
//    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getUsername());

        if(existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()) {
            result.rejectValue("login", null, "На этот адрес электронной почты уже зарегистрирована учетная запись.");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

}
