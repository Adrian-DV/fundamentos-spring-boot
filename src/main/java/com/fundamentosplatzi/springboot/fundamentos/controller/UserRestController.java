package com.fundamentosplatzi.springboot.fundamentos.controller;

import com.fundamentosplatzi.springboot.fundamentos.caseuse.CreateUserComponent;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.DeleteUserComponent;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.GetUser;
import com.fundamentosplatzi.springboot.fundamentos.caseuse.UpdateUserComponent;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private GetUser getUser;
    private CreateUserComponent createUserComponent;
    private DeleteUserComponent deleteUserComponent;
    private UpdateUserComponent updateUserComponent;

    public UserRestController(GetUser getUser, CreateUserComponent createUserComponent, DeleteUserComponent deleteUserComponent, UpdateUserComponent updateUserComponent) {
        this.getUser = getUser;
        this.createUserComponent=createUserComponent;
        this.deleteUserComponent=deleteUserComponent;
        this.updateUserComponent=updateUserComponent;
    }

    @GetMapping("/")
    List<User> get() {
        return getUser.getAll();
    }

    @PostMapping("/")
    ResponseEntity<User> newUser(@RequestBody User newUser){
        return new ResponseEntity<>(createUserComponent.save(newUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteUser(@PathVariable Long id){
        deleteUserComponent.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    ResponseEntity<User> replaceUser(@RequestBody User newUser, @PathVariable Long id){
     return new ResponseEntity<>(updateUserComponent.update(newUser,id), HttpStatus.OK);
    }

    @GetMapping("/Pageable")
    List<User> getUserPageable(@RequestParam int page, @RequestParam int size) {
        return getUser.getAllPageable(page, size);
    }
}
