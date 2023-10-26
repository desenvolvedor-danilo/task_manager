package com.dkmo.todolist.gerenciador_tarefas.tasks;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @PostMapping("/")
public ResponseEntity<?> inserirTarefa(@RequestBody Task task,HttpServletRequest request){
    var idUser = request.getAttribute("idUser");
    task.setIdUser((UUID) idUser);
    LocalDateTime localDateTime = LocalDateTime.now();
    task.setDataCriacao(localDateTime);
    if (localDateTime.isAfter(task.getInicio())){
     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hora/data inferior a atual");

    }
    if(task.getFim().isBefore(task.getInicio())){
        return ResponseEntity.status(400).body("A data/hora do fim da tarefa não pode ser inferior a data de início");
    }
var tarefa= taskRepository.save(task);

return ResponseEntity.status(200).body(tarefa);
}
@GetMapping("/")
public ResponseEntity<?> buscarTarefa(Task task,HttpServletRequest request){
    Object idUser = request.getAttribute("idUser");
    var id = taskRepository.findByIdUser((UUID)idUser);
    if(!id.isPresent()){
        return ResponseEntity.badRequest().body("você não logou para ver nenhuma tarefa");
    }
     return ResponseEntity.status(200).body(id.get());
} 
@PutMapping("/{id}")
public ResponseEntity<?>editarTarefa(@PathVariable UUID id,@RequestBody Task task, HttpServletRequest request){
    Object idUser = request.getAttribute("idUser");
    task.setIdUser((UUID) idUser);
    var busca = taskRepository.findById(id);
    LocalDateTime localDateTime = LocalDateTime.now();
    if (localDateTime.isAfter(task.getInicio())){
     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hora/data inferior a atual");

    }
    if(task.getFim().isBefore(task.getInicio())){
        return ResponseEntity.status(400).body("A data/hora do fim da tarefa não pode ser inferior a data de início");
    }
    if(!busca.isPresent()){
        return ResponseEntity.status(404).body("Tarefa não encontrada");
    }
    task.setDataCriacao(localDateTime);
    task.setId(id);
    BeanUtils.copyProperties(task,busca.get());
    

    return ResponseEntity.status(200).body(taskRepository.save(busca.get()));

}

}
