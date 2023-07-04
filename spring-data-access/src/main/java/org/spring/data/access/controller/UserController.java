package org.spring.data.access.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import org.spring.data.access.facade.BookingFacade;
import org.spring.data.access.model.User;
import org.spring.data.access.util.ClientRequestMetaInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/user")
public record UserController(BookingFacade bookingFacade) {

    @GetMapping("/{id:[\\d]+}")
    public User getUserById(@PathVariable Long id,
                            HttpServletRequest request) {
        ClientRequestMetaInfo.setClientIpAddress(request.getRemoteAddr());
        return bookingFacade.getUserById(id);
    }

    @GetMapping
    public User getUserByEmail(@RequestParam @Email String email) {
        return bookingFacade.getUserByEmail(email);
    }

    @GetMapping("/name")
    public Page<User> getUsersByName(@RequestParam String name,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "100") int size) {
        var pageable = PageRequest.of(page, size);
        return bookingFacade.getUsersByName(name, pageable);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return bookingFacade.createUser(user);
    }

    @PatchMapping
    public User updateUser(@RequestBody User user) {
        return bookingFacade.updateUser(user);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public void deleteUserById(@PathVariable Long id) {
        bookingFacade.deleteUser(id);
    }

}
