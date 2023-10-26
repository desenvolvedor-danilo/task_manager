package com.dkmo.todolist.gerenciador_tarefas.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,UUID> {
    Users findByUsername(String user);
    
}
