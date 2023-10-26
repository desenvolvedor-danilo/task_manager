package com.dkmo.todolist.gerenciador_tarefas.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.favre.lib.crypto.bcrypt.BCrypt;
@RestController
@RequestMapping("/users")

public class UsersController {
   @Autowired
    private UsersRepository usersRepository;
    
    @PostMapping("/")
    public ResponseEntity<?> inserirUsuario(@RequestBody Users users){ 
        var pass = BCrypt.withDefaults().hashToString(12, users.getSenha().toCharArray());  
        users.setSenha(pass);
        Users user = usersRepository.save(users);
       return ResponseEntity.status(200).body(user);
    }
}
