package com.example.remoteit;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/admin/users")
public class AdminController {
    private final AppUserRepository repository;

    public AdminController(AppUserRepository repository) {
        this.repository = repository;
    }

    
    @PutMapping("/{id}/activate")
    public UserResponse activate(@PathVariable long id) {
        AppUser user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không có user " + id));
        user.activate();
        return UserResponse.from(repository.save(user)); 
    }
}
