package br.com.joaog.todolist.Controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.joaog.todolist.Model.UserModel;
import br.com.joaog.todolist.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController{
    @Autowired
    private IUserRepository userRepository;
    @PostMapping
    public ResponseEntity create(@RequestBody UserModel user){
        var searchUser = this.userRepository.findByUsername(user.getUsername());
        if (searchUser != null){
            return ResponseEntity.status(400).body("Usuario já existe");
        }
        var passwordHash = BCrypt.withDefaults().hashToString(12,user.getPassword().toCharArray());
        user.setPassword(passwordHash);
        this.userRepository.save(user);
        return ResponseEntity.status(200).body("Usuario criado");
    }
}
