package ru.BookShop.my_book_shop.controller;

import jakarta.validation.Valid;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users/list")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        System.out.println(users);
        model.addAttribute("users", users);
        return "users";
    }


//    @RequestParam("id") Long id,
//    @RequestParam("firstName") String firstName,
//    @RequestParam("lastName") String lastName,
//

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/users/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") UserUpdateDto user,
                           @RequestParam("roles") Set<String> roleNames,
                           BindingResult result,
                           Model model) {

        // Создаем UserDto и заполняем данными
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setUsername(user.getUsername());
//        userDto.setFirstName(user.getFirstName());
//        userDto.setLastName(user.getLastName());
//        userDto.setPassword(user.getPassword());
//        userDto.setRoles(userDto.getRoles());

        userService.updateUser(user, roleNames);
        return "redirect:/admin/users/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users/updateUser")
    public ModelAndView updateUser(@RequestParam Long id) {

        ModelAndView mav = new ModelAndView("users-form");
        User user = userService.findUserById(id);
        mav.addObject("user", user);

        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users/deleteUser")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users/list";
    }
}

