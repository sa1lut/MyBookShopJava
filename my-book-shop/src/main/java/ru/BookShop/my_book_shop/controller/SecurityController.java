package ru.BookShop.my_book_shop.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.BookShop.my_book_shop.dto.UserDto;
import ru.BookShop.my_book_shop.dto.UserUpdateDto;
import ru.BookShop.my_book_shop.entity.Book;
import ru.BookShop.my_book_shop.entity.User;
import ru.BookShop.my_book_shop.service.UserService;

import java.util.*;

@Slf4j
@Controller
public class SecurityController {
    private UserService userService;
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

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
        log.info("POST /register/save сохранение пользователя: {}", userDto.getUsername());
        User existingUser = userService.findUserByUsername(userDto.getUsername());

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users/list")
    public String users(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET /admin/users/list получение списка пользователей пользователем: {}", userDetails.getUsername());
        List<UserDto> users = userService.findAllUsers();

        model.addAttribute("users", users);
        return "users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") UserUpdateDto user,
                           @RequestParam("roles") Set<String> roleNames,
                           @AuthenticationPrincipal UserDetails userDetails,
                           BindingResult result,
                           Model model) {
        log.info("POST /admin/users/saveUser сохранение пользователя: {} пользователем: {}", user.getUsername(), userDetails.getUsername());
        userService.updateUser(user, roleNames);
        return "redirect:/admin/users/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users/updateUser")
    public ModelAndView updateUser(@RequestParam Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET /admin/users/updateUser получение формы для редактирования пользователя пользователем: {}", userDetails.getUsername());
        ModelAndView mav = new ModelAndView("users-form");
        User user = userService.findUserById(id);
        mav.addObject("user", user);

        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users/deleteUser")
    public String deleteUser(@RequestParam Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("GET /admin/users/deleteUser удаление пользователя пользователем: {}", userDetails.getUsername());
        userService.deleteUser(id);
        return "redirect:/admin/users/list";
    }
}

