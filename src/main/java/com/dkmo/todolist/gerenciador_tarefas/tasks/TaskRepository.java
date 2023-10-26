package com.dkmo.todolist.gerenciador_tarefas.tasks;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,UUID> {
Optional<List<Task>>findByIdUser(UUID id);
}
